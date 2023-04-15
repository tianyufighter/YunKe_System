<template>
  <b-nav-item-dropdown
    right
    toggle-class="d-flex align-items-center dropdown-user-link"
    class="dropdown-user"
  >
    <template #button-content  v-if="userData != null">
      <div class="d-sm-flex d-none user-nav">
        <p class="user-name font-weight-bolder mb-0">
          {{ userData.username}}
        </p>
        <small class="text-primary">{{ roleName }}</small>
      </div>
      <b-avatar
        size="40"
        :src="userData.headImage"
        variant="light-primary"
      >
        <feather-icon
          v-if="!userData.username"
          icon="UserIcon"
          size="22"
        />
      </b-avatar>
    </template>

    <b-dropdown-item
      link-class="d-flex align-items-center"
      @click="logout"
    >
      <feather-icon
        size="16"
        icon="LogOutIcon"
        class="mr-50"
      />
      <span>退出登录</span>
    </b-dropdown-item></b-nav-item-dropdown>
</template>

<script>
import {
  BNavItemDropdown, BDropdownItem, BDropdownDivider, BAvatar,
} from 'bootstrap-vue'
import { avatarText } from '@core/utils/filter'

export default {
  components: {
    BNavItemDropdown,
    BDropdownItem,
    BDropdownDivider,
    BAvatar,
  },
  data() {
    return {
      userData: JSON.parse(localStorage.getItem('userData')),
      avatarText,
    }
  },
  computed: {
    roleName() {
      if (this.userData.role == 3) {
        return '超级管理员';
      } else if (this.userData.role == 2) {
        return '管理员';
      } else {
        return '普通用户';
      }
    }
  },
  methods: {
    logout() {
      // 移除token
      localStorage.removeItem("token")

      // 移除用户数据
      localStorage.removeItem('userData')

      // 重定向到登录页面
      this.$router.push({ name: 'auth-login' })
    },
  },
}
</script>
