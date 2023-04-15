<!--用户管理界面-->
<template>

  <div>

    <!--添加用户的弹框表单-->
    <user-list-add-new
      :is-add-new-user-sidebar-active.sync="isAddNewUserSidebarActive"
      :role-options="roleOptions"
      :gender-options="genderOptions"
      @updateUserSuccess="closeDialog"
    />

    <!-- 筛选用户 -->
    <users-list-filters
      :role-options="roleOptions"
      :gender-options="genderOptions"
      :status-options="statusOptions"
      @submitQuery="queryUserByCondition"
    />

    <!-- 显示用户的列表 -->
    <b-card
      no-body
      class="mb-0"
    >

      <div class="m-2">

        <!-- 表格头部 -->
        <b-row>

          <!-- 每页显示的条数 -->
          <b-col
            cols="12"
            md="6"
            class="d-flex align-items-center justify-content-start mb-1 mb-md-0"
          >
            <label>显示</label>
            <v-select
              v-model="perPage"
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="perPageOptions"
              :clearable="false"
              class="per-page-selector d-inline-block mx-50"
            />
            <label>条</label>
          </b-col>

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
                @click="isAddNewUserSidebarActive = true"
              >
                <span class="text-nowrap">添加用户</span>
              </b-button>
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="filterUsers"
        responsive
        :fields="tableColumns"
        primary-key="id"
        :sort-by.sync="sortBy"
        show-empty
        empty-text="没有发现符合条件的数据"
        :sort-desc.sync="isSortDirDesc"
      >

        <!-- Column: 用户名 -->
        <template #cell(username)="data">
          <b-media vertical-align="center">
            <template #aside>
              <b-avatar
                size="32"
                :src="data.item.headImage"
                :text="avatarText(data.item.username)"
                :variant="`light-${resolveUserRoleVariant(data.item.personType)}`"
              />
            </template>
            <b-link
              class="font-weight-bold d-block text-nowrap"
            >
              {{ data.item.username }}
            </b-link>
          </b-media>
        </template>

        <!-- Column: 角色 -->
        <template #cell(personType)="data">
          <div class="text-nowrap">
            <feather-icon
              :icon="resolveUserRoleIcon(data.item.personType)"
              size="18"
              class="mr-50"
              :class="`text-${resolveUserRoleVariant(data.item.personType)}`"
            />
            <span class="align-text-top text-capitalize">{{ getRoleName(data.item.personType) }}</span>
          </div>
        </template>

        <!-- Column: 注册时间 -->
        <template #cell(registryTime)="data">
            <span class="align-text-top text-capitalize">{{ dateFormat(data.item.registryTime) }}</span>
        </template>

        <!-- Column: 状态 -->
        <template #cell(status)="data">
          <b-badge
            pill
            :variant="`light-${resolveUserStatusVariant(data.item.isBan)}`"
            class="text-capitalize"
          >
            {{ getStatusName(data.item.isBan) }}
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

              <b-dropdown-item :to="{ name: 'user-edit', params: { id: data.item.id } }">
                <feather-icon icon="EditIcon" />
                <span class="align-middle ml-50">编辑</span>
              </b-dropdown-item>

              <b-dropdown-item>
                <feather-icon icon="TrashIcon" />
                <span class="align-middle ml-50" @click="deleteUserInfo(data.item.id)">删除</span>
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
              :total-rows="totalUsers"
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
  BBadge, BDropdown, BDropdownItem, BPagination,
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import { avatarText, title } from '@core/utils/filter'
import UsersListFilters from './UsersListFilters.vue'
import UserListAddNew from './UserListAddNew.vue'
import ToastificationContent from '@core/components/toastification/ToastificationContent.vue'
import { deleteUser, getAllUserByPage, getUserByCondition } from '@/network/usermanage'
import log from 'echarts/src/scale/Log'

export default {
  components: {
    UsersListFilters,
    UserListAddNew,

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
  data() {
    return {
      userData: JSON.parse(localStorage.getItem('userData')),
      roleOptions: [
        { label: '超级管理员', value: 'superAdmin' },
        { label: '管理员', value: 'admin' },
        { label: '普通用户', value: 'user' },
      ],
      genderOptions: [
        { label: '男', value: 'male' },
        { label: '女', value: 'female' },
        { label: '未知', value: 'unknown' },
      ],
      statusOptions: [
        { label: '正常', value: 'true' },
        { label: '封禁', value: 'false' },
      ],
      tableColumns: [
        { key: 'username', label: '用户名', sortable: true },
        { key: 'email', label: '邮箱', sortable: true },
        { key: 'personType', label: '角色', sortable: true },
        {
          key: 'registryTime',
          label: '注册时间',
          formatter: title,
          sortable: true,
        },
        { key: 'status', label: '状态', sortable: true },
        { key: 'actions', label: '操作' },
      ],
      perPage: 5, // 每页显示的条数
      searchQuery: '',
      totalUsers: 0,// 总的用户数量
      currentPage: 1,
      perPageOptions: [5, 10, 25, 50, 100],
      sortBy: 'id',
      isSortDirDesc: true,
      roleFilter: null,
      planFilter: null,
      statusFilter: null,
      refUserListTable: null,
      isAddNewUserSidebarActive: false,
      users: null // 所有的用户信息
    }
  },
  computed: {
    filterUsers() {
      return this.users?.filter((user) => {
        return (user.username.includes(this.searchQuery) || user.email.includes(this.searchQuery));
      })
    }
  },
  methods: {
    avatarText,
    queryUserByCondition(role, gender, status) {
      this.currentPage = 1;
      this.perPage = 5;
      getUserByCondition({
        pageNum: this.currentPage,
        pageSize: this.perPage,
        personType: role != null ? this.getPersonTypeByName(role.value) : null,
        gender: gender != null ? this.getGenderByName(gender.value) : null,
        isBan: status != null ? this.getStatusByName(status.value) : null
      }).then(res => {
        if (res.data.code == 200) {
          this.users = res.data.data.list;
          this.totalUsers = res.data.data.total;
          this.$vs.notify({
            title:'消息提示',
            text:'用户信息查询成功',
            color:'success',
            position:'top-center'})
        } else {
          Promise.reject(res);
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'用户信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
    deleteUserInfo(userId) {
      deleteUser(userId).then(res => {
        if (res.data.code == 200) {
          this.refetchData();
          this.$vs.notify({
            title:'消息提示',
            text:'用户信息删除成功',
            color:'success',
            position:'top-center'})
        }
      }).catch(res => {
        this.$vs.notify({
          title:'错误提示',
          text:'用户信息未能成功删除',
          color:'danger',
          position:'top-center'})
      })
    },
    closeDialog(onOrOff) {
      // 获取所有的用户信息
      getAllUserByPage().then(res => {
        this.users = res.data.data.list;
        this.totalUsers = res.data.data.total;
      }).catch(err => {
        this.$toast({
          component: ToastificationContent,
          toaster: 'b-toaster-top-center',
          props: {
            title: '获取用户数据失败',
            text: err.msg,
            icon: 'CoffeeIcon',
            variant: 'danger',
          },
        })
      });
      this.isAddNewUserSidebarActive = onOrOff;
    },
    //时间格式化函数，此处仅针对yyyy-MM-dd hh:mm:ss 的格式进行格式化
    dateFormat(time) {
      let date=new Date(time);
      let year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      let month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
      let day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
      // 拼接
      return year+"-"+month+"-"+day;
    },
    // 根据状态名称获取对应的码
    getStatusByName(statusName) {
      if (statusName == 'true') {
        return true;
      } else if (statusName == 'false') {
        return false;
      } else {
        return false;
      }
    },
    // 根据性别名称获取对应的码
    getGenderByName(genderName) {
      if (genderName == 'male') {
        return '男';
      } else if (genderName == 'female') {
        return '女';
      } else {
        return '未知';
      }
    },
    // 根据角色名称获取对应的码
    getPersonTypeByName(typeName) {
      if (typeName == 'user') {
        return 1;
      } else if (typeName == 'admin') {
        return 2;
      } else if (typeName == 'superAdmin') {
        return 3;
      } else {
        return 1;
      }
    },
    // 根据状态码获取状态的名称
    getStatusName(status) {
      if (status == true) {
        return "封禁";
      } else {
        return "正常";
      }
    },
    // 根据角色类型返回角色名称
    getRoleName(roleType) {
      if (roleType == 1) {
        return '普通用户';
      } else if (roleType == 2) {
        return '管理员';
      } else if (roleType == 3) {
        return '超级管理员';
      }
      return '普通人员'
    },
    resolveUserRoleVariant(roleType) {
      if (roleType == 1) return 'primary'
      if (roleType == 2) return 'warning'
      if (roleType == 3) return 'success'
      return 'primary'
    },
    resolveUserRoleIcon(roleType) {
      if (roleType == 1) return 'UserIcon'
      if (roleType == 2) return 'SettingsIcon'
      if (roleType == 3) return 'DatabaseIcon'
      return 'UserIcon'
    },
    resolveUserStatusVariant(status) {
      if (status == true) return 'secondary'
      if (status == false) return 'success'
      return 'primary'
    },
    refetchData() {
      // 获取所有的用户信息
      getAllUserByPage(this.currentPage, this.perPage).then(res => {
        this.users = res.data.data.list;
        this.totalUsers = res.data.data.total;
      }).catch(err => {
        this.$toast({
          component: ToastificationContent,
          toaster: 'b-toaster-top-center',
          props: {
            title: '获取用户数据失败',
            text: err.msg,
            icon: 'CoffeeIcon',
            variant: 'danger',
          },
        })
      })
    },
  },
  mounted() {
    // 获取所有的用户信息
    getAllUserByPage(this.currentPage, this.perPage).then(res => {
      this.users = res.data.data.list;
      this.totalUsers = res.data.data.total;
    }).catch(err => {
      this.$toast({
        component: ToastificationContent,
        toaster: 'b-toaster-top-center',
        props: {
          title: '获取用户数据失败',
          text: err.msg,
          icon: 'CoffeeIcon',
          variant: 'danger',
        },
      })
    })
  },
  watch: {
    perPage() {
      this.refetchData()
    },
    currentPage() {
      this.refetchData()
    },
    roleFilter() {
      this.refetchData()
    },
    genderFilter() {
      this.refetchData()
    },
    statusFilter() {
      this.refetchData()
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@core/scss/vue/libs/vue-select.scss';
.per-page-selector {
  width: 90px;
}
</style>
