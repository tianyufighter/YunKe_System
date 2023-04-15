<template>
  <component :is="userData === undefined ? 'div' : 'b-card'">

    <!-- Alert: No item found -->
    <b-alert
      variant="danger"
      :show="userData === undefined"
    >
      <h4 class="alert-heading">
        Error fetching user data
      </h4>
      <div class="alert-body">
        No user found with this user id. Check
        <b-link
          class="alert-link"
          :to="{ name: 'apps-users-list'}"
        >
          User List
        </b-link>
        for other users.
      </div>
    </b-alert>

    <b-tabs
      v-if="userData"
      pills
    >

      <!-- Tab: Account -->
      <b-tab active>
        <template #title>
          <feather-icon
            icon="UserIcon"
            size="16"
            class="mr-0 mr-sm-50"
          />
          <span class="d-none d-sm-inline">用户</span>
        </template>
        <user-edit-tab-account
          :user-data="userData"
          class="mt-2 pt-75"
        />
      </b-tab>

      <!-- Tab: Information -->
      <b-tab>
        <template #title>
          <feather-icon
            icon="InfoIcon"
            size="16"
            class="mr-0 mr-sm-50"
          />
          <span class="d-none d-sm-inline">重置密码</span>
        </template>
        <account-setting-password :userId="userData.id" class="mt-2 pt-75" />
      </b-tab>
    </b-tabs>
  </component>
</template>

<script>
import {
  BTab, BTabs, BCard, BAlert, BLink,
} from 'bootstrap-vue'
import UserEditTabAccount from './UserEditTabAccount.vue'
import UserEditTabInformation from './UserEditTabInformation.vue'
import AccountSettingPassword from '@/views/apps/system-manage/user-manage/users-edit/AccountSettingPassword.vue'
import {getUserById} from '@/network/usermanage'
import ToastificationContent from '@core/components/toastification/ToastificationContent.vue'

export default {
  components: {
    BTab,
    BTabs,
    BCard,
    BAlert,
    BLink,

    UserEditTabAccount,
    UserEditTabInformation,
    AccountSettingPassword
  },
  data() {
    return {
      userData: null,
    }
  },
  mounted() {
    getUserById(this.$router.currentRoute.params.id).
    then(res => {
      this.userData = res.data.data;
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
}
</script>

<style>

</style>
