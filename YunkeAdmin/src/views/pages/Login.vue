<template>
  <div class="auth-wrapper auth-v2">
    <b-row class="auth-inner m-0">

      <!-- Brand logo-->
      <b-link class="brand-logo">
        <vuexy-logo />
        <h2 class="brand-text text-primary ml-1">
          云客后台管理系统
        </h2>
      </b-link>
      <!-- /Brand logo-->

      <!-- Left Text-->
      <b-col
        lg="8"
        class="d-none d-lg-flex align-items-center p-5"
      >
        <div class="w-100 d-lg-flex align-items-center justify-content-center px-5">
          <b-img
            fluid
            :src="imgUrl"
            alt="Login V2"
          />
        </div>
      </b-col>
      <!-- /Left Text-->

      <!-- Login-->
      <b-col
        lg="4"
        class="d-flex align-items-center auth-bg px-2 p-lg-5"
      >
        <b-col
          sm="8"
          md="6"
          lg="12"
          class="px-xl-2 mx-auto"
        >
          <b-card-title
            class="mb-1 font-weight-bold"
            title-tag="h2"
          >
            欢迎使用云客后台管理系统
          </b-card-title>


          <!-- form -->
          <validation-observer
            ref="loginForm"
            #default="{invalid}"
          >
            <b-form
              class="auth-login-form mt-2"
              @submit.prevent="login"
            >
              <!-- email -->
              <b-form-group
                label="邮箱"
                label-for="login-email"
              >
                <validation-provider
                  #default="{ errors }"
                  name="Email"
                  vid="email"
                  rules="required|email"
                >
                  <b-form-input
                    id="login-email"
                    v-model="userEmail"
                    :state="errors.length > 0 ? false:null"
                    name="login-email"
                    placeholder="john@example.com"
                  />
                  <small class="text-danger">{{ errors[0] }}</small>
                </validation-provider>
              </b-form-group>

              <!-- forgot password -->
              <b-form-group>
                <div class="d-flex justify-content-between">
                  <label for="login-password">密码</label>
                </div>
                <validation-provider
                  #default="{ errors }"
                  name="Password"
                  vid="password"
                  rules="required"
                >
                  <b-input-group
                    class="input-group-merge"
                    :class="errors.length > 0 ? 'is-invalid':null"
                  >
                    <b-form-input
                      id="login-password"
                      v-model="password"
                      :state="errors.length > 0 ? false:null"
                      class="form-control-merge"
                      :type="passwordFieldType"
                      name="login-password"
                      placeholder="Password"
                    />
                    <b-input-group-append is-text>
                      <feather-icon
                        class="cursor-pointer"
                        :icon="passwordToggleIcon"
                        @click="togglePasswordVisibility"
                      />
                    </b-input-group-append>
                  </b-input-group>
                  <small class="text-danger">{{ errors[0] }}</small>
                </validation-provider>
              </b-form-group>

              <!-- checkbox -->
              <b-form-group>
                <b-form-checkbox
                  id="remember-me"
                  v-model="status"
                  name="checkbox-1"
                >
                  记住密码
                </b-form-checkbox>
              </b-form-group>

              <!-- submit buttons -->
              <b-button
                type="submit"
                variant="primary"
                block
                :disabled="invalid"
              >
                登录
              </b-button>
            </b-form>
          </validation-observer>
        </b-col>
      </b-col>
    <!-- /Login-->
    </b-row>
  </div>
</template>

<script>
/* eslint-disable global-require */
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import VuexyLogo from '@core/layouts/components/Logo.vue'
import {
  BRow, BCol, BLink, BFormGroup, BFormInput, BInputGroupAppend, BInputGroup, BFormCheckbox, BCardText, BCardTitle, BImg, BForm, BButton, BAlert, VBTooltip,
} from 'bootstrap-vue'
import { required, email } from '@validations'
import { togglePasswordVisibility } from '@core/mixins/ui/forms'
import store from '@/store'
import { loginSystem } from '@/network/usermanage'

import ToastificationContent from '@core/components/toastification/ToastificationContent.vue'

export default {
  directives: {
    'b-tooltip': VBTooltip,
  },
  components: {
    BRow,
    BCol,
    BLink,
    BFormGroup,
    BFormInput,
    BInputGroupAppend,
    BInputGroup,
    BFormCheckbox,
    BCardText,
    BCardTitle,
    BImg,
    BForm,
    BButton,
    BAlert,
    VuexyLogo,
    ValidationProvider,
    ValidationObserver,
  },
  mixins: [togglePasswordVisibility],
  data() {
    return {
      status: '',
      password: '',
      userEmail: '',
      sideImg: require('@/assets/images/pages/login-v2.svg'),

      // validation rules
      required,
      email,
    }
  },
  computed: {
    passwordToggleIcon() {
      return this.passwordFieldType === 'password' ? 'EyeIcon' : 'EyeOffIcon'
    },
    imgUrl() {
      if (store.state.appConfig.layout.skin === 'dark') {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.sideImg = require('@/assets/images/pages/login-v2-dark.svg')
        return this.sideImg
      }
      return this.sideImg
    },
  },
  mounted() {
    let userEmail = this.$cookies.get("userEmail");
    if (userEmail !== null && this.$cookies.get(userEmail) != null) {
      this.userEmail = userEmail;
      this.password = this.$cookies.get(userEmail);
    }
  },
  methods: {
    login() {
      this.$refs.loginForm.validate().then(success => {
        if (success) {
          // 实现记住密码功能
          if (this.status === true) {
            // 设置存储3天
            this.$cookies.set(this.userEmail, this.password, '3d');
            this.$cookies.set("userEmail", this.userEmail, '3d');
          }
          loginSystem({
            email: this.userEmail,
            password: this.password
          }).then(res => {
            if (res.data.code == 200) {
              const userData = {
                role: res.data.data.role,
                username: res.data.data.username,
                userId: res.data.data.id,
                headImage: res.data.data.headImage
              }
              localStorage.setItem('userData', JSON.stringify(userData));
              localStorage.setItem("token", res.data.data.token);
              this.$router.replace(this.getHomeRouteForLoggedInUser(res.data.data.role)).then(() => {
                this.$toast({
                  component: ToastificationContent,
                  position: 'top-center',
                  props: {
                    title: `欢迎${res.data.data.username}`,
                    icon: 'CoffeeIcon',
                    variant: 'success',
                    text: `你作为${this.getRoleName(res.data.data.role)}已经登录成功. 现在你可以开始探索了!`,
                  },
                })
              })
            } else {
              this.$vs.notify({
                title:'错误提示',
                text: res.data.message,
                color:'danger',
                position:'top-center'})
            }
          }).catch(err => {
            console.log("err = ", err)
            this.$vs.notify({
              title:'错误提示',
              text:'登录验证错误，请重新登录',
              color:'danger',
              position:'top-center'})
          })
        }
      })
    },
    getHomeRouteForLoggedInUser(userRole) {
      if (userRole == 1) {
        return {name: 'auth-login'}
      } else {
        return '/'
      }
    },
    getRoleName(roleType) {
      if (roleType == '1') {
        return '普通用户'
      } else if (roleType == '2') {
        return '管理员'
      } else {
        return '超级管理员'
      }
    }
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/pages/page-auth.scss';
</style>
