<!--用户管理界面-->
<template>

  <div>

    <!-- 筛选用户 -->
    <system-notice-filters
      :status-options="options"
      @queryNoticeCondition="getNoticeCondition"
      @reGetData="refetchData"
    />

    <!-- 显示用户的列表 -->
    <b-card
      no-body
      class="mb-0"
    >

      <div class="m-2">
        <!-- 表格头部 -->
        <b-row>
          <b-col cols="12" md="6"></b-col>
          <!-- 搜索 -->
          <b-col
            cols="12"
            md="6"
          >
            <div class="d-flex align-items-center justify-content-end">
              <b-form-input
                v-model="searchQuery"
                class="d-inline-block mr-1"
                placeholder="搜索..."
              />
              <b-button
                variant="primary"
                @click="showModal"
              >
                <span class="text-nowrap">新增公告</span>
              </b-button>
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="filterNoticeList"
        responsive
        :fields="tableColumns"
        primary-key="id"
        :sort-by.sync="sortBy"
        show-empty
        empty-text="No matching records found"
        :sort-desc.sync="isSortDirDesc"
      >

        <!-- Column: 通知状态 -->
        <template #cell(status)="data">
          <b-badge
            pill
            :variant="`light-${resolveStatusVariant(data.item.status)}`"
            class="text-capitalize"
          >
            {{ data.item.status == true ? '正常' : '关闭' }}
          </b-badge>
        </template>


        <!-- Column: 操作 -->
        <template #cell(actions)="data">
          <b-dropdown
              variant="link"
              no-caret
              :right="$store.state.appConfig.isRTL"
          >

            <template #button-content>
              <feather-icon
                  icon="MoreVerticalIcon"
                  size="16"
                  class="align-middle text-body"
              />
            </template>

            <b-dropdown-item  @click="showModal(data.item)">
              <feather-icon icon="EditIcon" />
              <span class="align-middle ml-50">编辑</span>
            </b-dropdown-item>

            <b-dropdown-item @click="deleteNotice(data.item.id)">
              <feather-icon icon="TrashIcon" />
              <span class="align-middle ml-50">删除</span>
            </b-dropdown-item>
          </b-dropdown>
        </template>
      </b-table>
      <div class="mx-2 mb-2">
        <b-row>

          <b-col
            cols="12"
            sm="6"
            class="d-flex align-items-center justify-content-center justify-content-sm-start"
          >
          </b-col>
          <!-- Pagination -->
          <b-col
            cols="12"
            sm="6"
            class="d-flex align-items-center justify-content-center justify-content-sm-end"
          >

            <b-pagination
              v-model="currentPage"
              :total-rows="totalNotice"
              :per-page="perPage"
              first-number
              last-number
              class="mb-0 mt-1 mt-sm-0"
              prev-class="prev-item"
              next-class="next-item"
            >
              <template #prev-text>
                <feather-icon
                  icon="ChevronLeftIcon"
                  size="18"
                />
              </template>
              <template #next-text>
                <feather-icon
                  icon="ChevronRightIcon"
                  size="18"
                />
              </template>
            </b-pagination>

          </b-col>

        </b-row>
      </div>
    </b-card>
    <!-- select 2 demo -->
    <b-modal
        ref="modal-select2"
        title="添加公告"
        ok-title="发布"
        cancel-title="取消"
        @ok="submitNotice"
        cancel-variant="outline-secondary"
    >
      <b-form>
        <b-form-group
            label="状态"
        >
          <b-form-radio-group
              v-model="status"
              :options="statusOptions"
              class="demo-inline-spacing mb-1"
              value-field="value"
              text-field="text"
              disabled-field="disabled"
          />
        </b-form-group>

        <b-form-group
            label="内容"
            label-for="username"
        >
          <b-form-textarea
              id="username"
              v-model="noticeContent"
              trim
          />
        </b-form-group>

      </b-form>
    </b-modal>
  </div>
</template>

<script>
import {
  BCard, BRow, BCol, BFormInput, BButton, BTable, BMedia, BAvatar, BLink,
  BBadge, BDropdown, BDropdownItem, BPagination, VBModal, BForm, BFormGroup,BFormTextarea, BFormRadioGroup
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import SystemNoticeFilters from './SystemNoticeFilters.vue'
import Ripple from 'vue-ripple-directive'
import {listNotice, updateNotice, addNotice, delNotice} from '@/network/noticemanage'

export default {
  components: {
    SystemNoticeFilters,
    BFormRadioGroup,
    BFormTextarea,
    BFormGroup,
    BForm,
    BCard,
    BRow,
    BCol,
    BFormInput,
    BButton,
    BTable,
    BMedia,
    BAvatar,
    BLink,
    BBadge,
    BDropdown,
    BDropdownItem,
    BPagination,

    vSelect,
  },
  directives: {
    'b-modal': VBModal,
    Ripple,
  },
  data() {
    return {
      selected: 'first',
      options: [
        { label: '正常', value: 'true' },
        { label: '关闭', value: 'false'},
      ],
      statusOptions: [
        {text: '正常', value: 'true'},
        {text: '关闭', value: 'false'}
      ],
      selectedSystemInner: 'yes',
      tableColumns: [
        { key: 'id', label: '序号', sortable: true },
        { key: 'noticeContent', label: '公告内容', sortable: true },
        { key: 'status', label: '状态', sortable: true },
        { key: 'createTime', label: '创建时间' },
        { key: 'actions', label: '操作' },
      ],
      perPage: 10, // 每页显示的条数
      searchQuery: '',
      totalNotice: 0, // 系统总的通知数量
      currentPage: 1,
      perPageOptions: [10, 25, 50, 100],
      sortBy: 'id',
      isSortDirDesc: true,
      refUserListTable: null,
      isAddNewUserSidebarActive: false,
      noticeList: null,
      // 以下是新增或者修改公告消息所用到的变量
      id: null,
      noticeContent: null,
      status: null,
    }
  },
  computed: {
    filterNoticeList() {
      return this.noticeList?.filter(notice => {
        return notice.noticeContent.includes(this.searchQuery);
      })
    }
  },
  methods: {
    deleteNotice(noticeId) {
      delNotice(noticeId).then(res => {
        if (res.data.code == 200) {
          this.refetchData();
          this.$vs.notify({
            title:'消息提示',
            text:'通知公告信息删除成功',
            color:'success',
            position:'top-center'})
        } else {
          this.$vs.notify({
            title:'错误提示',
            text:'通知公告信息删除失败',
            color:'danger',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'通知公告信息删除失败',
          color:'danger',
          position:'top-center'})
      })
    },
    getNoticeCondition(noticeContent, status) {
      this.currentPage = 1;
      this.perPage = 10;
      listNotice({
        pageNum: this.currentPage,
        pageSize: this.perPage,
        noticeContent: noticeContent,
        status: status
      }).then(res => {
        if (res.data.code == 200) {
          this.noticeList = res.data.data.list;
          this.totalNotice = res.data.total;
        } else {
          this.$vs.notify({
            title:'错误提示',
            text:'通知公告信息查询失败',
            color:'danger',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'通知公告信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
    clearForm() {
      // 清除表单数据
      this.noticeContent = null;
      this.status = null;
    },
    // 修改或添加公告
    submitNotice() {
      if (this.id != null) {
        updateNotice({
          id: this.id,
          noticeContent: this.noticeContent,
          status: this.status
        }).then(res => {
          if (res.data.code == 200) {
            this.refetchData();
            this.clearForm();
            this.$vs.notify({
              title:'消息提示',
              text:'通知公告信息修改成功',
              color:'success',
              position:'top-center'})
          } else {
            this.$vs.notify({
              title:'错误提示',
              text:'通知公告信息修改失败',
              color:'danger',
              position:'top-center'})
          }
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'通知公告信息修改失败',
            color:'danger',
            position:'top-center'})
        })
      } else {
        addNotice({
          noticeContent: this.noticeContent,
          status: this.status
        }).then(res => {
          if (res.data.code == 200){
            this.refetchData();
            this.clearForm();
            this.$vs.notify({
              title:'消息提示',
              text:'通知公告信息添加成功',
              color:'success',
              position:'top-center'})
          } else {
            this.$vs.notify({
              title:'错误提示',
              text:'通知公告信息添加失败',
              color:'danger',
              position:'top-center'})
          }
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'通知公告信息添加失败',
            color:'danger',
            position:'top-center'})
        })
      }
    },
    showModal(data) {
      this.id = data.id;
      this.noticeContent = data.noticeContent;
      this.status = data.status;
      this.$refs['modal-select2'].show();
    },
    resolveStatusVariant(status) {
      if (status == true) return 'success'
      if (status == false) return 'secondary'
      return 'success';
    },
    refetchData() {
      listNotice({
        pageNum: this.currentPage,
        pageSize: this.perPage
      }).then(res => {
        if (res.data.code == 200) {
          this.noticeList = res.data.data.list;
          this.totalNotice = res.data.data.total;
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'通知公告信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
  },
  mounted() {
    listNotice({
      pageNum: this.currentPage,
      pageSize: this.perPage
    }).then(res => {
      if (res.data.code == 200) {
        this.noticeList = res.data.data.list;
        this.totalNotice = res.data.data.total;
      }
    }).catch(err => {
      this.$vs.notify({
        title:'错误提示',
        text:'通知公告信息查询失败',
        color:'danger',
        position:'top-center'})
    })
  },
  watch: {
    currentPage() {
      this.refetchData()
    },
  }
}
</script>

<style lang="scss" scoped>
.per-page-selector {
  width: 90px;
}
</style>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
