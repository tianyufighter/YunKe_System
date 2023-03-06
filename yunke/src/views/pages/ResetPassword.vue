<!-- 重置密码界面 -->
<template>
    <div class="h-screen flex w-full bg-img">
        <div class="vx-col sm:w-3/5 md:w-3/5 lg:w-3/4 xl:w-3/5 mx-auto self-center">
            <vx-card>
                <div slot="no-body" class="full-page-bg-color">
                    <div class="vx-row">
                        <div class="vx-col hidden sm:hidden md:hidden lg:block lg:w-1/2 mx-auto self-center">
                            <img src="@/assets/images/pages/reset-password.png" alt="login" class="mx-auto">
                        </div>
                        <div class="vx-col sm:w-full md:w-full lg:w-1/2 mx-auto self-center  d-theme-dark-bg">
                            <div class="p-8">
                                <div class="vx-card__title mb-8">
                                    <h4 class="mb-4">重置密码</h4>
                                    <p>请输入你的新密码</p>
                                </div>
                                <vs-input type="email" label-placeholder="邮箱" v-model="email" class="w-full mb-6" />
                                <vs-input type="password" label-placeholder="密码" v-model="password" class="w-full mb-6" />
                                <vs-input type="password" label-placeholder="确认密码" v-model="confirm_password" class="w-full mb-8" />

                                <div class="flex flex-wrap justify-between flex-col-reverse sm:flex-row">
                                    <vs-button type="border" to="/pages/login" class="w-full sm:w-auto mb-8 sm:mb-auto mt-3 sm:mt-auto">返回到登录界面</vs-button>
                                    <vs-button class="w-full sm:w-auto" @click="resetPassword">重置</vs-button>
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
import {doResetPassword} from "../../network";
export default {
  data() {
      return {
        password: '',
        email: '',
        key: '',
        username: '',
        confirm_password: ''
      }
  },
  mounted() {
    this.username = this.$route.query.username;
    this.key = this.$route.query.key;
  },
  methods: {
    resetPassword() {
      doResetPassword({
        email: this.email,
        password: this.password,
        username: this.username,
        key: this.key
      }).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          this.email="";
          this.password="";
          this.confirm_password="";
          this.$router.push("/pages/login")
          this.$vs.notify({
            title:'通知',
            text:"密码重置成功",
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
