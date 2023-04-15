<!--
  个人中心页面
-->

<template>
    <div id="demo-basic-card">
        <div class="vx-row">
          <!--COL 1-->
          <div class="vx-col w-full lg:w-1/4">
            <vx-card class="p-2 mt-base">
              <div class="text-center">
                <h4>{{ username }}</h4>
              </div>
              <vs-avatar color="primary" :text="username && username.length > 2 ? username.substr(0, 2) : username" :src="imageUrl" size="80px" class="mx-auto my-6 block"/>
              <div class="flex justify-between text-center">
                <span>
                    <p class="text-xl font-semibold">{{ totalReleasePostNum }}</p>
                    <small class="text-grey">{{ card.user_meta_1_label }}</small>
                </span>
                <span>
                    <p class="text-xl font-semibold">{{totalReleaseInfoNum}}</p>
                    <small class="text-grey">{{ card.user_meta_2_label }}</small>
                </span>
                <span>
                    <p class="text-xl font-semibold">{{ totalReleaseBlogNum}}</p>
                    <small class="text-grey">{{ card.user_meta_3_label }}</small>
                </span>
              </div>
              <vs-button type="gradient" class="w-full mt-6" color="#7367F0" gradient-color-secondary="#CE9FFC">{{ jobTitle }}</vs-button>
            </vx-card>
            <vx-card class="overlay-card overflow-hidden mt-base">
              <template slot="no-body">
                <img :src="require(`@/assets/images/pages/${card_7.overlay_img}`)" alt="user-profile-cover" class="responsive">
                <div class="card-overlay text-white flex flex-col justify-between">
                  <h4 class="text-white mb-4">{{ card_7.title }}</h4>
                  <p>{{ describe }}</p>
                </div>
              </template>
            </vx-card>
          </div>
          <!--COL 2-->
          <div class="vx-col w-full lg:w-3/4">
            <vx-card class="mt-base">
              <vs-tabs vs-alignment="fixed" alignment="fixed">
                <!--修改个人资料模块-->
                <vs-tab vs-label="PersonalInformation" vs-icon="account_circle" label="个人资料">
                  <div class="mt-3">
                    <div class="vx-col w-full mb-base">
                      <vx-card>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>头像</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                            <el-upload
                                class="avatar-uploader"
                                action="/api/common/file/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                              <img v-if="imageUrl" :src="imageUrl" class="avatar">
                              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>用户名</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                            <vs-input class="w-full"  v-model="username" />
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>出生日期</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                            <div style="display: flex;justify-content: left">
                              <span style="margin-right: 1rem">
                                <datepicker ref="programaticOpen" :language="languages[language]" :value="birthday" format="yyyy-MM-dd"></datepicker>
                              </span>
                              <vs-button class="mb-4" @click="$refs.programaticOpen.showCalendar()">编辑</vs-button>
                            </div>
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>性别</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                              <ul style="display: flex;">
                                <li style="margin-right: 1.5rem">
                                  <vs-radio v-model="gender" vs-value="男">男</vs-radio>
                                </li>
                                <li>
                                  <vs-radio v-model="gender" vs-value="女">女</vs-radio>
                                </li>
                              </ul>
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>邮箱</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                            <vs-input type="email" class="w-full" v-model="email" />
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>电话号码</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                            <vs-input class="w-full" v-model="phone" />
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>职位</span>
                          </div>
                          <div class="vx-col sm:w-2/3 w-full">
                            <vs-input type="text" class="w-full" v-model="jobTitle" />
                          </div>
                        </div>
                        <div class="vx-row mb-6">
                          <div class="vx-col sm:w-1/3 w-full">
                            <span>标语</span>
                          </div>
                          <div class="vx-col lg:w-1/2 w-full">
                            <div>
                              <vs-textarea counter="20" label="最大字数: 20" :counter-danger.sync="counterDanger" v-model="describe" />
                            </div>
                          </div>
                        </div>
                        <div class="vx-row">
                          <div class="vx-col sm:w-2/3 w-full ml-auto">
                            <vs-button class="mr-3 mb-2" @click="updateUser">保存</vs-button>
                          </div>
                        </div>
                      </vx-card>
                    </div>
                  </div>
                </vs-tab>
                <!--修改个人密码模块-->
                <vs-tab vs-label="ResetPassword" vs-icon="message" label="更改密码">
                  <div class="mt-3">
                    <vx-card>
                      <div class="mt-5">
                        <form>
                          <div class="flex justify-center mt-base">
                            <div class="vx-row lg:w-1/2 w-full">
                              <div class="vx-col w-full">
                                <vs-input v-validate="'required|alpha'" placeholder="用户名" name="name" v-model="name" class="mt-5 w-full" />
                                <span class="text-danger text-sm"  v-show="errors.has('name')">用户名不能为空</span>
                              </div>

                              <div class="vx-col lg:w-1/2 w-full">
                                <vs-input v-validate="'required|email'" placeholder="邮箱" name="email" v-model="uEmail" class="mt-5 w-full" />
                                <span class="text-danger text-sm" v-show="errors.has('email')">邮箱不能为空</span>
                              </div>

                              <div class="vx-col lg:w-1/2 w-full">
                                <vs-input type="password" v-validate="'required|min:1|max:15'" placeholder="原密码" name="old_password" v-model="old_password" class="mt-5 w-full" />
                                <span class="text-danger text-sm" v-show="errors.has('old_password')">要求输入原密码</span>
                              </div>

                              <div class="vx-col lg:w-1/2 w-full">
                                <vs-input type="password" v-validate="'required|min:1|max:15'" ref="password" placeholder="新密码" name="password" v-model="password" class="mt-5 w-full" />
                                <span class="text-danger text-sm" v-show="errors.has('password')">请输入密码</span>
                              </div>

                              <div class="vx-col lg:w-1/2 w-full">
                                <vs-input type="password" v-validate="'required|min:1|max:15|confirmed:password'" data-vv-as="password" placeholder="确认密码" name="confirm_password" v-model="confirm_password" class="mt-5 w-full" />
                                <span class="text-danger text-sm" v-show="errors.has('confirm_password')">需要确认密码</span>
                              </div>

                            </div>
                          </div>
                          <div class="flex justify-center mt-base">
                            <vs-button type="filled" @click.prevent="submitForm" class="mt-5 block">更改</vs-button>
                          </div>
                        </form>
                      </div>
                    </vx-card>
                  </div>
                </vs-tab>
              </vs-tabs>
            </vx-card>
          </div>
        </div>
    </div>
</template>

<script>
import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import { videoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'
import Datepicker from 'vuejs-datepicker';
import * as lang from 'vuejs-datepicker/src/locale';
import {getAllUserInfo, getInfoNum} from "../../network";
import {doModifyUserInfo} from "../../network";
import {doUpdatePassword} from "../../network";

export default{
    data() {
        return {
            card: {
                userImg: '',
                user_meta_1_label: '帖子量',
                user_meta_2_label: '信息发布量',
                user_meta_3_label: '博客量',
            },

            card_7: {
                overlay_img: 'card-image-6.jpg',
                title: '标语',
                // text: 'Cake sesame snaps cupcake gingerbread danish I love gingerbread. Apple pie pie jujubes chupa chups muffin halvah lollipop.',
            },
            totalReleaseBlogNum: null,
            totalReleasePostNum: null,
            totalReleaseInfoNum: null,
            name: '',
            username: '',
            email: '',
            uEmail:'',
            msg: '',
            password: "",
            confirm_password: "",
            old_password: "",
            describe: '',
            counterDanger: false,
            languages: lang,
            language: "zh",
            gender: '男',
            imageUrl: 'https://i.imgur.com/ezM6SJ5.png',
            phone: "1234334",
            jobTitle: "职称未标注",
            birthday: "2021-12-23",

            playerOptions: {
                height: '460',
                fluid: true,
                autoplay: false,
                muted: true,
                language: 'en',
                playbackRates: [0.7, 1.0, 1.5, 2.0],
                sources: [ { type: "video/mp4", src: "http://vjs.zencdn.net/v/oceans.mp4" } ],
                poster: 'https://goo.gl/xcCsDd',
            },
            settings: { // perfectscrollbar settings
                maxScrollbarLength: 60,
                wheelSpeed: .60,
            },
            wasSidebarOpen: null,
        }
    },
    components: {
        VuePerfectScrollbar,
        videoPlayer,
        Datepicker
    },
    // mounted() {
    //     this.$refs.chatLogPS.$el.scrollTop = this.$refs.chatLog.scrollHeight;
    // },
    methods: {
      // 更新密码
      submitForm() {
        this.$validator.validateAll().then(result => {
          if(result) {
            doUpdatePassword({
              username: this.name,
              email: this.uEmail,
              oldPassword: this.old_password,
              newPassword: this.password
            }).then(res => {
              if (res.data.code === 200) {
                this.name = "";
                this.uEmail = "";
                this.old_password = "";
                this.password = "";
                this.confirm_password = "";
                this.$vs.notify({
                  title:'密码修改成功',
                  text:res.data["message"],
                  color:'success',
                  position:'top-right'})
              }
            }).catch(err => {
              console.log(err)
            })
          }else{
            // form have errors
          }
        })
      },
      // 更新用户信息
      updateUser() {
        doModifyUserInfo({
          headImage: this.imageUrl,
          username: this.username,
          birthday: this.birthday,
          gender: this.gender,
          email: this.email,
          phone: this.phone,
          jobTitle: this.jobTitle,
          description: this.describe
        }).then(res => {
          if (res.data.code === 200) {
            localStorage.setItem("username", this.username);
            localStorage.setItem("headImage", this.imageUrl);
            this.$store.commit('updateAvatar', this.imageUrl);
            this.$store.commit('updateUsername', this.username);
            this.$vs.notify({ title: '更新提示', text: '用户信息修改成功', color: 'success', position: 'top-center' })
          }
        }).catch(err => {
          console.log(err)
        })
      },
      handleAvatarSuccess(res, file) {
        console.log("res = ", res)
        console.log("file = ", file)
        this.imageUrl = file.response.data.url;
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$vs.notify({
            title:'错误提示',
            text:'上传头像图片只能是 JPG 格式!',
            color:'danger',
            position:'top-right'})
        }
        if (!isLt2M) {
          this.$vs.notify({
            title:'错误提示',
            text:'上传头像图片大小不能超过 2MB!',
            color:'danger',
            position:'top-right'})
        }

        return isJPG && isLt2M;
      },
      openAlert() {
        this.$vs.dialog({
          color: 'warning',
          title: `警告`,
          text: '登录状态已过期，请重新登录。',
          accept: this.acceptAlert
        })
      },
      acceptAlert() {
        localStorage.clear();
        this.$router.push("/pages/login");
        this.$vs.notify({
          color: 'primary',
          title: '接收',
          text: '跳转到登录界面'
        })
      }
  },
  mounted() {
    this.wasSidebarOpen = this.$store.state.reduceButton;
    this.$store.commit('TOGGLE_REDUCE_BUTTON', true);
    this.totalReleaseBlogNum = this.$store.state.totalReleaseBlog;
    this.totalReleasePostNum = this.$store.state.totalReleasePost;
    // 获取总的信息发布量
    // getInfoNum().then(res => {
    //   if (res.data.code == 200) {
    //     this.totalReleaseInfoNum = res.data.data;
    //   }
    // }).catch(err => {
    //   console.log("err = ", err)
    // })
  },
  created() {
      // 请求用户数据
      getAllUserInfo().then(res => {
        this.username = res.data.data.username;
        this.email = res.data.data.email;
        this.phone = res.data.data.phone;
        this.jobTitle = res.data.data.jobTitle !== "" ? res.data.data.jobTitle : "职称未标注";
        this.describe = res.data.data.description;
        this.gender = res.data.data.gender;
        this.imageUrl = res.data.data.headImage;
        this.birthday = res.data.data.birthday.substr(0, 10);
      }).catch(err => {
        if (err.data.code && err.data.code === 411) {
          this.openAlert();
        } else {
          console.log(err);
        }
      })
  },
  beforeDestroy() {
    if (!this.wasSidebarOpen) this.$store.commit('TOGGLE_REDUCE_BUTTON', false)
  }
}
</script>

<style lang="scss">
#demo-basic-card {
    .overlay-card {
        .vx-card__collapsible-content {
            max-height: 485px;
        }
    }

    .chat-card-log {
        height: 360px;

        .chat-sent-msg {
            background-color: #f2f4f7 !important;
        }
    }

    .card-video {
        .video-js {
            height: 370px;
        }
    }
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 5rem;
  height: 5rem;
  display: block;
}
</style>
