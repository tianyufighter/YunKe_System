<!--登录页面-->
<template>
    <div class="h-screen flex w-full bg-img vx-row no-gutter items-center justify-center" id="page-login">
        <div class="vx-col sm:w-1/2 md:w-1/2 lg:w-3/4 xl:w-3/5 sm:m-0 m-4">
          <div id="div-with-loading">
            <vx-card>
              <div slot="no-body" class="full-page-bg-color">
                <div class="vx-row no-gutter justify-center items-center">
                  <div class="vx-col hidden lg:block lg:w-1/2">
                    <img src="@/assets/images/pages/login.png" alt="login" class="mx-auto">
                  </div>
                  <div class="vx-col sm:w-full md:w-full lg:w-1/2 d-theme-dark-bg">
                    <div class="p-8">
                      <div class="vx-card__title mb-8">
                        <h4 class="mb-4">登录</h4>
                        <p>欢迎使用云客在线服务系统, 请登录你的账户</p>
                      </div>
                      <!--用户名输入框-->
                      <vs-input
                          data-vv-validate-on="blur"
                          v-validate="'required'"
                          type="username"
                          name="username"
                          icon="icon icon-user"
                          icon-pack="feather"
                          label-placeholder="用户名"
                          v-model="username"
                          class="w-full no-icon-border" />
                      <span class="text-danger text-sm" v-show="errors.has('username')">用户名不能为空</span>
                      <!--密码输入框-->
                      <vs-input
                          @focus="autoFillPassword"
                          data-vv-validate-on="blur"
                          v-validate="'required|min:1|max:16'"
                          type="password"
                          name="password"
                          icon="icon icon-lock"
                          icon-pack="feather"
                          label-placeholder="密码"
                          v-model="password"
                          class="w-full mt-6 no-icon-border" />
                      <span class="text-danger text-sm" v-show="errors.has('password')">密码不能为空(至少8位)</span>
                      <div class="flex flex-wrap justify-between" v-if="isCaptchaEnabled">
                        <!--验证码输入框-->
                        <vs-input
                            data-vv-validate-on="blur"
                            v-validate="'required'"
                            type="text"
                            name="verifyCode"
                            icon="icon icon-pocket"
                            icon-pack="feather"
                            label-placeholder="验证码"
                            v-model="verifyCode"
                            class="w-full mt-6 no-icon-border lg:w-3/5" />
                        <!--显示验证码图片-->
                        <div class="w-full mt-6 no-icon-border lg:w-2/5" style="padding-left: 1rem;">
                          <img style="cursor: pointer;width: 100%;height: 100%;" @click="refresh" :src="imgUrl"/>
                        </div>
                      </div>
                      <div class="flex flex-wrap justify-between my-5">
                        <vs-checkbox v-model="checkbox_remember_me" class="mb-3">记住密码</vs-checkbox>
                        <router-link to="/pages/forgot-password">忘记密码?</router-link>
                      </div>
                      <vs-button  type="border" @click="registerUser">注册</vs-button>
                      <vs-button class="float-right" :disabled="!validateForm" @click="login">登录</vs-button>
                    </div>
                  </div>
                </div>
              </div>
            </vx-card>
          </div>
        </div>
    </div>
</template>

<script>
import {doLogin, getCaptchaEnabled, getCaptcha} from "../../network";
export default {
    data() {
        return {
            username: '',
            password: '',
            verifyCode: '', // 用户输入的验证码
            imgUrl: '', // 验证码的url地址
            checkbox_remember_me: false,
            key: '', // redis中存储验证码的key
            isCaptchaEnabled: false // 是否开启验证码
        }
    },
    created() {
      getCaptchaEnabled().then(res => {
        if (res.data.data == true) {
          // 获取验证码
          getCaptcha().then(res => {
            this.isCaptchaEnabled = true;
            // 获取响应头中的redis—key
            if (res.headers['redis-key']) {
              this.key = res.headers['redis-key'];
            }
            let blob = new Blob([res.data], {type: 'image/jpeg'})
            this.imgUrl = window.URL.createObjectURL(blob)
          }).catch(err => {
            console.log(err)
          })
        }
      }).catch(err => {
        console.log(err)
      })
    },
    computed: {
          validateForm() {
            if (this.isCaptchaEnabled) {
              return !this.errors.any() && this.username !== '' && this.password !== '' && this.verifyCode !== '';
            } else {
              return !this.errors.any() && this.username !== '' && this.password !== '';
            }
          },
    },
    methods: {
        // 刷新验证码
        refresh() {
          this.verifyCode = ''
          getCaptcha().then(res => {
            // 获取响应头中的redis—key
            if (res.headers['redis-key']) {
              this.key = res.headers['redis-key'];
            }
            let blob = new Blob([res.data], {type: 'image/jpeg'})
            this.imgUrl = window.URL.createObjectURL(blob)
          }).catch(err => {
            console.log(err)
          })
        },
        // 执行登录功能
        login() {
          // this.$vs.loading({
          //   container: '#div-with-loading',
          //   scale: 0.6
          // })
          // 实现记住密码功能
          if (this.checkbox_remember_me === true) {
            // 设置存储3天
            this.$cookies.set(this.username, this.password, '3d');
          }
          // 发送登录请求
          doLogin({
            verifyCode: this.verifyCode,
            username: this.username,
            password: this.password
          }, this.key).then(res => {
            // this.$vs.loading.close('#div-with-loading > .con-vs-loading')
            if (res.data["code"] !== 200) {
              this.refresh();
              this.$vs.notify({
                title:'错误提示',
                text:res.data["message"],
                color:'danger',
                position:'top-right'})
            } else {
              localStorage.setItem("role", res.data.data.role);
              localStorage.setItem("token", res.data.data.token);
              localStorage.setItem("username", res.data.data.username);
              localStorage.setItem("userId", res.data.data.id);
              localStorage.setItem("headImage", res.data.data.headImage);
              this.$router.push('/');
            }
          }).catch(err => {
            this.$vs.loading.close('#div-with-loading > .con-vs-loading')
            console.log(err)
          })
        },
        notifyAlreadyLogedIn() {
            this.$vs.notify({
                title: 'Login Attempt',
                text: 'You are already logged in!',
                iconPack: 'feather',
                icon: 'icon-alert-circle',
                color: 'warning'
            });
        },
        registerUser() {
            this.$router.push('/pages/register');
        },
        autoFillPassword() {
            if (this.username != '' && this.$cookies.get(this.username) != null) {
              this.password = this.$cookies.get(this.username);
            }
        }
    }
}
</script>

<style lang="scss">
#page-login {
    .social-login {
        .bg-facebook {
          background-color: #1551b1;
        }
        .bg-twitter {
          background-color: #00aaff;
        }
        .bg-google {
          background-color: #4285F4;
        }
        .bg-github {
          background-color: #333;
        }
    }
}
</style>
