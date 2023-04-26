<!--用户管理界面-->
<template>

  <div>
    <!-- 筛选用户 -->
    <param-setting-filters
      :status-options="statusOptions"
      @clickSearch="fetchConfigByCondition"
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
                v-if="userData.role == 3"
              >
                <span class="text-nowrap">新增参数</span>
              </b-button>
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="filterConfigList"
        responsive
        :fields="tableColumns"
        primary-key="id"
        :sort-by.sync="sortBy"
        show-empty
        empty-text="No matching records found"
        :sort-desc.sync="isSortDirDesc"
      >
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

            <b-dropdown-item  @click="showModal(data.item)" v-if="userData.role == 3">
              <feather-icon icon="EditIcon" />
              <span class="align-middle ml-50">编辑</span>
            </b-dropdown-item>

            <b-dropdown-item @click="clickDelete(data.item)" v-if="userData.role == 3">
              <feather-icon icon="TrashIcon" />
              <span class="align-middle ml-50">删除</span>
            </b-dropdown-item>

            <b-dropdown-item v-if="userData.role != 3">
              <span class="align-middle text-warning">无权限操作</span>
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
              :total-rows="totalConfig"
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
        title="修改参数"
        ok-title="更改"
        cancel-title="取消"
        cancel-variant="outline-secondary"
        @cancel=""
        @hidden=""
        @ok="submitConfig"
    >
      <b-form>
        <b-form-group
            label="参数名称"
            label-for="configName"
        >
          <b-form-input
              id="configName"
              v-model="configName"
              trim
          />
        </b-form-group>

        <b-form-group
            label="参数键名"
            label-for="configKey"
        >
          <b-form-input
              v-model="configKey"
              id="configKey"
              trim
          />
        </b-form-group>

        <b-form-group
            label="参数键值"
            label-for="configValue"
        >
          <b-form-input
              v-model="configValue"
              id="configValue"
              trim
          />
        </b-form-group>

        <b-form-group
            label="系统内置"
            label-for="configType"
        >
          <v-select
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="statusOptions"
              :reduce="val => val.value"
              :clearable="false"
              v-model="configType"
              input-id="configType"
          />
        </b-form-group>

        <b-form-group
            label="备注"
            label-for="remark"
        >
          <b-form-textarea
              v-model="remark"
              id="remark"
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
  BBadge, BDropdown, BDropdownItem, BPagination, VBModal, BForm, BFormGroup,BFormTextarea
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import { avatarText, title } from '@core/utils/filter'
import ParamSettingFilters from './ParamSettingFilters.vue'
import Ripple from 'vue-ripple-directive'
import { listConfig, updateConfig, addConfig, delConfig } from '@/network/configmanage'

export default {
  components: {
    ParamSettingFilters,
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
      userData: JSON.parse(localStorage.getItem('userData')),
      selectedSystemInner: 'yes',
      statusOptions: [
        { label: '是', value: 'Y' },
        { label: '否', value: 'N' },
      ],
      tableColumns: [
        { key: 'id', label: '参数主键', sortable: true },
        { key: 'configName', label: '参数名称', sortable: true },
        { key: 'configKey', label: '参数键名', sortable: true },
        { key: 'configValue', label: '参数键值', formatter: title, sortable: true, },
        { key: 'configType', label: '系统内置', sortable: true },
        { key: 'remark', label: '备注' },
        { key: 'createTime', label: '创建时间' },
        { key: 'actions', label: '操作' },
      ],
      perPage: 2, // 每页显示的条数
      searchQuery: '', // 搜索所对应的关键字
      totalConfig: 0, // 总的配置数
      currentPage: 1,
      perPageOptions: [10, 25, 50, 100],
      sortBy: 'id',
      isSortDirDesc: true,
      configList: null,
      // 下面是一个参数配置所需要的数据
      id: null,
      configName: null,
      configKey: null,
      configValue: null,
      configType: null,
      userId: null,
      createTime: null,
      remark: null
    }
  },
  computed: {
    filterConfigList() {
      return this.configList?.filter(config => {
        return (config.configName?.includes(this.searchQuery) ||
                config.configKey?.includes(this.searchQuery) ||
                config.configValue?.includes(this.searchQuery) ||
                config.remark?.includes(this.searchQuery)
        )
      })
    }
  },
  methods: {
    // 重新获取所有的参数配置
    refetchConfigList() {
      listConfig({
        pageNum: this.currentPage,
        pageSize: this.perPage
      }).then(res => {
        if (res.data.code == 200) {
          this.configList = res.data.data.list;
          this.totalConfig = res.data.data.total;
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
    submitConfig() {
      if (this.id == null) {
        addConfig({
          configName: this.configName,
          configKey: this.configKey,
          configValue: this.configValue,
          configType: this.configType,
          createTime: this.createTime,
          remark: this.remark,
        }).then(res => {
          if (res.data.code == 200) {
            this.refetchConfigList();
            this.clearForm();
            this.$refs['modal-select2'].hide();
            this.$vs.notify({
              title:'消息提示',
              text:'参数信息添加成功',
              color:'success',
              position:'top-center'})
          } else {
            this.$vs.notify({
              title:'错误提示',
              text: res.data.data,
              color:'danger',
              position:'top-center'})
          }
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'参数信息添加失败',
            color:'danger',
            position:'top-center'})
        })
      } else {
        updateConfig({
          id: this.id,
          configName: this.configName,
          configKey: this.configKey,
          configValue: this.configValue,
          configType: this.configType,
          userId: this.userId,
          createTime: this.createTime,
          remark: this.remark,
        }).then(res => {
          if (res.data.code == 200) {
            this.refetchConfigList();
            this.clearForm();
            this.$refs['modal-select2'].hide();
            this.$vs.notify({
              title:'消息提示',
              text:'参数信息已成功修改',
              color:'success',
              position:'top-center'})
          } else {
            Promise.reject(res);
          }
        }).catch(err=> {
          this.$vs.notify({
            title:'错误提示',
            text:'参数信息修改失败',
            color:'danger',
            position:'top-center'})
        })
      }
    },
    // 清除表单数据
    clearForm() {
      this.id = null;
      this.configName = null;
      this.configKey = null;
      this.configValue = null;
      this.configType = null;
      this.userId = null;
      this.createTime = null;
      this.remark = null;
    },
    // 根据配置的id删除对应的配置信息
    clickDelete(data) {
      delConfig(data.id).then(res => {
        if (res.data.code == 200) {
          this.refetchConfigList();
          this.$vs.notify({
            title:'消息提示',
            text:'配置信息已成功删除',
            color:'success',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'配置信息删除失败',
          color:'danger',
          position:'top-center'})
      })
    },
    showModal(data) {
      this.id = data.id;
      this.configName = data.configName;
      this.configKey = data.configKey;
      this.configValue = data.configValue;
      this.configType = data.configType;
      this.userId = data.userId;
      this.createTime = data.createTime;
      this.remark = data.remark;
      this.$refs['modal-select2'].show();
    },
    fetchConfigByCondition(paramName, paramKey, isSystemInner) {
      listConfig({
        pageNum: this.currentPage,
        pageSize: this.perPage,
        configName: paramName,
        configKey: paramKey,
        configType: isSystemInner
      }).then(res => {
        if (res.data.code == 200) {
          this.configList = res.data.data.list;
          this.totalConfig = res.data.data.total;
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
    }
  },
  mounted() {
    listConfig({
      pageNum: this.currentPage,
      pageSize: this.perPage
    }).then(res => {
      if (res.data.code == 200) {
        this.configList = res.data.data.list;
        this.totalConfig = res.data.data.total;
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
      this.refetchConfigList()
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
