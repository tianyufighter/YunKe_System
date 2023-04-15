<!--
  注册界面
-->
<template>
    <div class="h-screen flex w-full bg-img vx-row no-gutter items-center justify-center">
        <div class="vx-col sm:w-1/2 md:w-1/2 lg:w-3/4 xl:w-3/5 sm:m-0 m-4">
            <vx-card>
                <div slot="no-body" class="full-page-bg-color">
                    <div class="vx-row no-gutter">
                        <div class="vx-col hidden sm:hidden md:hidden lg:block lg:w-1/2 mx-auto self-center">
                            <img src="@/assets/images/pages/register.jpg" alt="register" class="mx-auto">
                        </div>
                        <div class="vx-col sm:w-full md:w-full lg:w-1/2 mx-auto self-center  d-theme-dark-bg">
                            <div class="p-8">
                                <div class="vx-card__title">
                                    <h4 class="mb-4">创建账户</h4>
                                    <p>填写表单信息来创建新账户</p>
                                </div>
                                <div class="clearfix">
                                    <vs-input
                                        v-validate="'required'"
                                        data-vv-validate-on="blur"
                                        label-placeholder="Username"
                                        name="username"
                                        placeholder="用户名"
                                        v-model="username"
                                        class="w-full" />
                                    <span class="text-danger text-sm" v-show="errors.has('username')">用户名不能为空</span>

                                    <vs-input
                                        v-validate="'required|email'"
                                        data-vv-validate-on="blur"
                                        name="email"
                                        type="email"
                                        label-placeholder="Email"
                                        placeholder="邮箱"
                                        v-model="email"
                                        class="w-full mt-6" />
                                    <span class="text-danger text-sm" v-show="errors.has('email')">邮箱不符合格式要求</span>

                                    <vs-input
                                        ref="password"
                                        type="password"
                                        data-vv-validate-on="blur"
                                        v-validate="'required|min:1|max:15'"
                                        name="password"
                                        label-placeholder="Password"
                                        placeholder="密码"
                                        v-model="password"
                                        class="w-full mt-6" />
                                    <span class="text-danger text-sm" v-show="errors.has('password')">密码要求输入</span>

                                    <vs-input
                                        type="password"
                                        v-validate="'min:1|max:15|confirmed:password'"
                                        data-vv-validate-on="blur"
                                        data-vv-as="password"
                                        name="confirm_password"
                                        label-placeholder="Confirm Password"
                                        placeholder="确认密码"
                                        v-model="confirm_password"
                                        class="w-full mt-6" />
                                    <span class="text-danger text-sm" v-show="errors.has('confirm_password')">确认密码要求输入</span>

                                    <div class="flex flex-wrap justify-between">
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

                                    <vs-checkbox v-model="isTermsConditionAccepted" class="mt-6">我同意注册</vs-checkbox>
                                    <vs-button  type="border" to="/pages/login" class="mt-6">登录</vs-button>
                                    <vs-button class="float-right mt-6" @click="registerUser" :disabled="!validateForm">注册</vs-button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </vx-card>
        </div>
    </div>
</template>

<script>
import {getCaptcha} from "../../network";
import {doRegister} from "../../network";

export default {
    created() {
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
    data() {
        return {
            username: '',
            email: '',
            password: '',
            confirm_password: '',
            isTermsConditionAccepted: false,
            verifyCode: '', // 用户输入的验证码
            imgUrl: '', // 验证码的url地址
            key: '' // redis中存储验证码的key
        }
    },
    computed: {
        validateForm() {
            return !this.errors.any() && this.username != '' && this.email != '' && this.password != '' && this.confirm_password != '' && this.verifyCode != '' && this.isTermsConditionAccepted === true;
        }
    },
    methods: {
        registerUser() {
            if (!this.validateForm) return false
            doRegister({
              username: this.username,
              password: this.password,
              email: this.email,
              verifyCode: this.verifyCode
            }, this.key).then(res=> {
              if (res.data.code !== 200) {
                this.refresh();
                this.$vs.notify({
                  title:'错误提示',
                  text:res.data.message,
                  color:'danger',
                  position:'top-right'})
              } else {
                this.$vs.dialog({
                  color: 'success',
                  title: `注册通知`,
                  text: '注册验证链接已成功发送到了你的邮箱，请及时查收',
                  accept: this.acceptAlert
                })
                this.$router.push('/');
              }
            }).catch(err => {
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
      // 刷新验证码
      refresh() {
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
    }
}
</script>
