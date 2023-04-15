<!--
  忘记密码界面
-->
<template>
    <div class="h-screen flex w-full bg-img">
        <div class="vx-col w-4/5 sm:w-4/5 md:w-3/5 lg:w-3/4 xl:w-3/5 mx-auto self-center">
            <vx-card>
                <div slot="no-body" class="full-page-bg-color">
                    <div class="vx-row">
                        <div class="vx-col hidden sm:hidden md:hidden lg:block lg:w-1/2 mx-auto self-center">
                            <img src="@/assets/images/pages/forgot-password.png" alt="login" class="mx-auto">
                        </div>
                        <div class="vx-col sm:w-full md:w-full lg:w-1/2 mx-auto self-center d-theme-dark-bg">
                            <div class="p-8">
                                <div class="vx-card__title mb-8">
                                    <h4 class="mb-4">找回你的密码</h4>
                                    <p>请输入你的邮箱账号并且我们将会给你指示如何重置你的密码</p>
                                </div>

                                <vs-input type="email" label-placeholder="Email" v-model="value1" class="w-full mb-8" />
                                <vs-button type="border" to="/pages/login" class="px-4 w-full md:w-auto">返回登录</vs-button>
                                <vs-button class="float-right px-4 w-full md:w-auto mt-3 mb-8 md:mt-0 md:mb-0" @click="resetPassword">重置密码</vs-button>
                            </div>
                        </div>
                    </div>
                </div>
            </vx-card>
        </div>
    </div>
</template>

<script>
import {doForgetPassword} from '@network'
export default {
    data() {
        return {
            value1: ''
        }
    },
  methods: {
    resetPassword() {
      doForgetPassword({
        email: this.value1
      }).then(res => {
        if (res.data.code === 200) {
          this.value1='';
          this.$vs.notify({
            title:'通知',
            text:res.data["message"],
            color:'success',
            position:'top-center'})
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>
