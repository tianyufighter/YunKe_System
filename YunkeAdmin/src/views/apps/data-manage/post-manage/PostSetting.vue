<!--用户管理界面-->
<template>
  <div>
    <post-setting-add-new @onSubmit="submitPost" ref="addOrUpdatePost" :is-add-new-user-sidebar-active="isAddNewUserSidebarActive" @updateStatus="changeStatus"/>
    <!-- 筛选用户 -->
    <post-setting-filters
      :status-options="statusOptions"
      @clickSearch="fetchConfigByCondition"
      @clickReset="refetchPostList"
    />

    <!-- 显示用户的列表 -->
    <b-card
      no-body
      class="mb-0"
    >

      <div class="m-2">
        <!-- 表格头部 -->
        <b-row>
          <b-col cols="12" md="8">
            <b-button
                variant="primary"
                @click="deletePost"
            >
              <span class="text-nowrap">删除</span>
            </b-button>
          </b-col>
          <!-- 搜索 -->
          <b-col
            cols="12"
            md="4"
          >
            <div class="d-flex align-items-center justify-content-end">
              <b-form-input
                v-model="searchQuery"
                class="d-inline-block mr-1"
                placeholder="搜索..."
              />
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="filterPostList"
        responsive
        :fields="tableColumns"
        primary-key="id"
        :sort-by.sync="sortBy"
        show-empty
        empty-text="No matching records found"
        :sort-desc.sync="isSortDirDesc"
      >
        <template v-slot:head(#)="data">
          <b-form-checkbox v-model="allchecked" @change="toggleAll" :indeterminate="indeterminate"></b-form-checkbox>
        </template>
        <template v-slot:cell(#)="row">
          <b-form-checkbox @change="checkboxClick(row.item.id)" v-model="isCheckedObj['index'+row.item.id]"></b-form-checkbox>
        </template>
        <!--Column: 标题-->
        <template #cell(title)="data">
          <b-badge
              pill
              :variant="`light-info`"
              class="text-capitalize"
          >
            <span v-if="data.item.length > 10">{{data.item.title | truncate(10) | tailing('...')}}</span>
            <span v-else>{{data.item.title}}</span>
          </b-badge>
        </template>
        <!--Column: 内容-->
        <template #cell(content)="data">
          <b-badge
              pill
              :variant="`light-dark`"
              class="text-capitalize"
          >
            <span v-if="data.item.length > 20">{{data.item.content | truncate(20) | tailing('...')}}</span>
            <span v-else>{{data.item.content}}</span>
          </b-badge>
        </template>
        <!--Column: 图片-->
        <template #cell(postCover)="data">
          <img :src="data.item.postCover" style="height: 80px" v-if="data.item.postCover != null && data.item.postCover != ''"/>
          <b-badge
              pill
              :variant="`light-primary`"
              class="text-capitalize"
              v-else
          >
            空
          </b-badge>
        </template>
        <!--Column: 发布时间-->
        <template #cell(createTime)="data">
          <span>{{data.item.createTime.replace("T"," ")}}</span>
        </template>
        <!--Column: 状态-->
        <template #cell(isViolation)="data">
          <b-badge
              pill
              :variant="`light-${resolveStatusVariant(data.item.isViolation)}`"
              class="text-capitalize"
          >
            {{ data.item.isViolation == true ? '违规' : '正常' }}
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

            <b-dropdown-item @click="deletePost(data.item)">
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
              :total-rows="totalPost"
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
  </div>
</template>

<script>
import {
  BCard, BRow, BCol, BFormInput, BButton, BTable, BMedia, BAvatar, BLink,
  BBadge, BDropdown, BDropdownItem, BPagination, VBModal, BForm, BFormGroup,BFormTextarea,BFormCheckbox,
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import { avatarText, title } from '@core/utils/filter'
import PostSettingFilters from '@/views/apps/data-manage/post-manage/PostSettingFilters.vue'
import PostSettingAddNew from '@/views/apps/data-manage/post-manage/PostSettingAddNew.vue'
import Ripple from 'vue-ripple-directive'
import { listConfig, updateConfig, addConfig, delConfig } from '@/network/configmanage'
import {getPost, updatePost, deletePostBatch} from '@/network/postmanage'

export default {
  components: {
    PostSettingAddNew,
    PostSettingFilters,
    BFormTextarea,
    BFormGroup,
    BFormCheckbox,
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
      selectedSystemInner: 'yes',
      statusOptions: [
        { label: '正常', value: false },
        { label: '封禁', value: true },
      ],
      tableColumns: [
        { key: '#', label: '#'},
        { key: 'title', label: '帖子标题', sortable: true },
        { key: 'content', label: '帖子内容', sortable: true },
        { key: 'postCover', label: '帖子图片', formatter: title, sortable: true, },
        { key: 'createTime', label: '发布时间' },
        { key: 'isViolation', label: '状态' },
        { key: 'userId', label: '用户id' },
        { key: 'actions', label: '操作' },
      ],
      perPage: 5, // 每页显示的条数
      searchQuery: '', // 搜索所对应的关键字
      totalPost: 0, // 总的帖子数
      currentPage: 1,
      perPageOptions: [10, 25, 50, 100],
      sortBy: 'id',
      isSortDirDesc: true,
      postList: null,
      isAddNewUserSidebarActive: false,
      post: {}, // 用户点击某个帖子时，就临时存储该帖子的数据
      idArr: [],
      allchecked: false,
      indeterminate: false,
      isCheckedObj: {}
    }
  },
  computed: {
    filterPostList() {
      return this.postList?.filter(post => {
        return (post.title?.includes(this.searchQuery) ||
                post.content?.includes(this.searchQuery)
        );
      })
    }
  },
  filters: {
    truncate(value, limit) {
      return value.substring(0, limit);
    },
    tailing(value, tail) {
      return value + tail;
    }
  },
  methods: {
    deletePost(data) {
      if (data != null) {
        this.idArr.push(data.id);
      }
      deletePostBatch(this.idArr).then(res => {
        if (res.data.code == 200 ) {
          this.refetchPostList();
          this.$vs.notify({
            title:'消息提示',
            text:'帖子信息删除成功',
            color:'success',
            position:'top-center'})
        } else {
          this.$vs.notify({
            title:'错误提示',
            text:'帖子信息删除失败',
            color:'danger',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'帖子信息删除失败',
          color:'danger',
          position:'top-center'})
      })
    },
    changeStatus(val) {
      this.isAddNewUserSidebarActive = val;
    },
    // 重新获取所有的的帖子信息
    refetchPostList() {
      getPost({
        pageNum: this.currentPage,
        pageSize: this.perPage
      }).then(res => {
        if (res.data.code == 200) {
          this.postList = res.data.data.list;
          this.totalPost = res.data.data.total;
          this.postList.forEach(post => {
            this.$set(this.isCheckedObj, 'index' + post.id, false);
          })
        } else {
          Promise.reject(res);
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'参数信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
    resolveStatusVariant(data) {
      if (data == true) {
        return 'danger';
      } else {
        return 'success'
      }
    },
    showModal(data) {
      this.$refs.addOrUpdatePost.id = data?.id;
      this.$refs.addOrUpdatePost.content = data?.content;
      this.$refs.addOrUpdatePost.title = data?.title;
      this.$refs.addOrUpdatePost.postCover = data?.postCover;
      this.$refs.addOrUpdatePost.isViolation = data?.isViolation;
      this.isAddNewUserSidebarActive = true;
    },
    submitPost(data) {
      this.isAddNewUserSidebarActive = false;
      updatePost(data).then(res => {
        if (res.data.code == 200) {
          this.refetchPostList();
          this.$vs.notify({
            title:'消息提示',
            text:'更新帖子成功',
            color:'success',
            position:'top-center'})
        } else {
          this.$vs.notify({
            title:'错误提示',
            text:'更新帖子信息失败',
            color:'danger',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'更新帖子信息失败',
          color:'danger',
          position:'top-center'})
      })
    },
    // 点击全选按钮
    toggleAll(checked) {
      this.idArr = []
      this.postList.forEach(element => {
        if (checked) {
          this.idArr.push(element.id);
        }
        this.isCheckedObj['index' + element.id] = checked;
      });
    },
    // 复选框点击选中与取消
    checkboxClick(id) {
      let index = this.idArr.indexOf(id);
      if (index > -1) {
        this.idArr.splice(index, 1);
      } else {
        this.idArr.push(id);
      }
    }
  },
  mounted() {
    this.isCheckedObj = {};
    getPost({
      pageNum: this.currentPage,
      pageSize: this.perPage
    }).then(res => {
      if (res.data.code == 200) {
        this.postList = res.data.data.list;
        this.totalPost = res.data.data.total;
        this.postList.forEach(post => {
          this.$set(this.isCheckedObj, 'index' + post.id, false);
        })
      } else {
        Promise.reject(res);
      }
    }).catch(err => {
      this.$vs.notify({
        title:'错误提示',
        text:'参数信息查询失败',
        color:'danger',
        position:'top-center'})
    })
  },
  watch: {
    currentPage() {
      this.refetchPostList();
    },
    idArr: {
      handler(newVal, oldVal) {
        if (newVal.length == 0) {
          this.indeterminate = false;
          this.allchecked = false;
        } else if (newVal.length == this.postList.length) {
          this.indeterminate = false;
          this.allchecked = true;
        } else {
          this.indeterminate = true;
          this.allchecked = false;
        }
      },
      deep: true // 深度
    }
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
