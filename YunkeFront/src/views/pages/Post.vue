<!-- 
    菜单栏中社区对应的社区界面
-->
<template>
    <div id="profile-page">
<!--        <add-post-card :isSidebarActive="isReleaseCard" @closeSidebar="isReleaseCard = false" />-->
        <add-post-card :isSidebarActive="isReleaseCard" @closeSidebar="closeSidebar" />
        <!-- PROFILE HEADER -->
        <div class="profile-header">
            <div class="relative">
                <div class="cover-container rounded-t-lg">
                    <img :src="require(`@/assets/images/profile/user-uploads/${userCoverImg}`)" alt="user-profile-cover" class="responsive block">
                </div>
                <div class="profile-img-container pointer-events-none">
                    <div>
                        <vs-avatar color="primary" class="user-profile-img" :text="userInfo.username && userInfo.username.length > 2 ? userInfo.username.substr(0, 2) : userInfo.username" :src="userInfo.profileImg" size="85px" />
                    </div>
                    <div class="profile-actions pointer-events-auto flex" @click="isReleaseCard = true">
                        <vs-button icon-pack="feather" radius icon="icon-edit-2"></vs-button>
                        <!-- <vs-button icon-pack="feather" radius icon="icon-settings" class="ml-2 lg:ml-4"></vs-button> -->
                    </div>
                </div>
            </div>
            <div class="flex items-center justify-end flex-wrap profile-header-nav p-6">

                <div class="block sm:hidden">
                    <feather-icon @click="isNavOpen = !isNavOpen" icon="AlignJustifyIcon" v-show="!isNavOpen" class="vx-navbar-toggler" />
                    <feather-icon icon="XIcon" v-show="isNavOpen" @click="isNavOpen = !isNavOpen" class="vx-navbar-toggler" />
                </div>
            </div>
        </div>

        <!-- COL AREA -->
        <div class="vx-row">
            <!-- COL 1 -->
            <div class="vx-col w-full lg:w-3/4">
                <vx-card class="mt-base" v-for="(post, index) in userPosts" :key="index">
                    <div>
                        <!-- POST HEADER -->
                        <div class="post-header flex justify-between mb-4">
                            <div class="flex items-center">
                                <div>
                                    <vs-avatar class="m-0" :src="post.user.headImage" size="45px" @click="enterChat(post)"></vs-avatar>
                                </div>
                                <div class="ml-4">
                                    <h6>{{ post.user.username }}</h6>
                                    <small>{{ post.createTime.replace("T", " ")}}</small>
                                </div>
                            </div>
                            <div class="flex">
                                <feather-icon class="ml-4" icon="HeartIcon" :svgClasses="{'text-danger fill-current stroke-current': post.isLike}" @click="isLike(post)"></feather-icon>
                            </div>
                        </div>
                        <span v-if="post.title !== ''" style="font-weight: bolder" class="hover:text-black text-grey">{{post.title}}</span>
                        <!-- POST CONTENT -->
                        <div class="post-content">
                            <p>{{ post.content }}</p>
                        </div>
                        <div class="post-media-container mb-6 mt-4">
                            <ul class="flex post-media-list">
                                <li class="post-media m-1 w-full">
                                    <img style="width: 20rem" class="responsive rounded" :src="post.postCover">
                                </li>
                            </ul>
                        </div>

                        <!-- POST ACTION DATA -->
                        <div>
                            <div class="flex justify-between">
                                <div class="flex items-center">
                                    <div class="flex items-center"><feather-icon class="mr-2" icon="HeartIcon" svgClasses="h-5 w-5"></feather-icon> <span>{{ post.likes }}</span></div>
                                    <ul class="users-liked user-list ml-3 sm:ml-6">
                                        <li v-for="(user, userIndex) in post.likesUser" :key="userIndex">
                                            <vx-tooltip :text="user.username" position="bottom">
                                                <vs-avatar :src="user.headImage" size="30px" class="border-2 border-white border-solid -m-1"></vs-avatar>
                                            </vx-tooltip>
                                        </li>
                                    </ul>
                                    <small v-if="post.likes !== 0" class="ml-2">+{{ post.likes > 5 ? post.likes - 5 : post.likes }} more</small>
                                </div>
                                <div class="flex items-center"><feather-icon class="mr-2" icon="MessageSquareIcon" svgClasses="h-5 w-5"></feather-icon> <span>{{ post.commentList.length }}</span></div>
                            </div>
                            <div class="comments-container mt-4">
                                <ul class="user-comments-list">
                                    <li v-for="(commentedUser, commentIndex) in post.commentList.slice(0, post.len)" :key="commentIndex" class="commented-user flex items-center mb-4">
                                        <div class="mr-3"><vs-avatar class="m-0" :src="commentedUser.headImage" size="30px" /></div>
                                        <div class="leading-tight">
                                            <p class="font-medium">{{ commentedUser.username }}</p>
                                            <span class="text-xs">{{ commentedUser.content }}</span>
                                        </div>
                                        <div class="ml-auto">
                                            <div class="flex">
                                              <span class="text-primary">{{commentedUser.createTime.replace("T", " ")}}</span>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                                <span class="flex justify-end" v-if="post.commentList.length > post.len">
                                    <a class="inline-flex items-center text-sm" href="javascript:;" @click="post.len = post.commentList.length"><span>View All</span> <feather-icon icon="ChevronsRightIcon" svgClasses="h-4 w-4"></feather-icon></a>
                                </span>
                            </div>
                            <div class="post-comment">
                                <vs-textarea label="comment" class="mb-2" v-model="post.commentbox" />
                                <vs-button size="small" @click="releaseComment(post)">发表评论</vs-button>
                            </div>
                        </div>
                    </div>
                </vx-card>
            </div>

            <!-- COL 2 -->
            <div class="vx-col w-full lg:w-1/4">
                <vx-card title="社区活跃达人" class="mt-base">
                    <!-- ACTION SLOT -->
                    <template slot="actions">
                        <feather-icon icon="MoreHorizontalIcon"></feather-icon>
                    </template>

                    <!-- FRIENDS LIST -->
                    <ul class="friend-suggesions-list">
                        <li class="friend-suggestion flex items-center mb-4" v-for="(friend, index) in suggestedFriends" :key="index">
                            <div class="mr-3"><vs-avatar class="m-0" :src="friend.headImage" size="35px" /></div>
                            <div class="leading-tight">
                                <p class="font-medium">{{ friend.username }}</p>
                                <span class="text-xs">{{ friend.postCount }} 篇帖子</span>
                            </div>
                            <div class="ml-auto cursor-pointer">
                                <vs-button icon-pack="feather" icon="icon-user-plus" @click="addContactPerson(friend)" />
                            </div>
                        </li>
                    </ul>
<!--                    <template slot="footer">-->
<!--                    <vs-button icon-pack="feather" icon="icon-plus" class="w-full" @click="loadMoreActive">Load More</vs-button>-->
<!--                    </template>-->
                </vx-card>
            </div>
        </div>

        <div class="vx-row">
            <div class="vx-col w-full lg:w-3/4">
                <div class="flex justify-center mt-base">
                    <vs-button id="button-load-more-posts" class="vs-con-loading__container" @click="loadContent">Load More</vs-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { videoPlayer } from 'vue-video-player'
import AddPostCard from './AddPostCard.vue';
import 'video.js/dist/video-js.css'
import {doAddFriend, getPostByPage} from "../../network";
import {doLikePost} from "../../network";
import {getActivePerson} from "../../network";
import {doReleaseComment} from "../../network";

export default {
  data() {
      return {
        pageNum: 1, // 帖子
        pageSize: 2, // 帖子
        activePageNum: 1, // 社区活跃达人
        activePageSize: 10, // 社区活跃达人
          isReleaseCard: false,
          isNavOpen: false,
          userPoll: '',
          userInfo: {
              username: '',
              profileImg: '',
          },
          mediaExtensions: { img: ['jpg', 'jpeg', 'png', 'bmp', 'gif', 'exif', 'tiff'], video: ['avi', 'flv', 'wmv', 'mov', 'mp4', '3gp'] },
          suggestedFriends: [
          ],
          userCoverImg: 'cover.jpg',
          userPosts: [],
          wasSidebarOpen: null,
      }
  },
  computed: {
      mediaType() {
          return (media) => {
              if (media.img) {
                  const ext = media.img.split('.').pop();
                  if (this.mediaExtensions.img.includes(ext)) return 'image'
              } else if (media.sources && media.poster) {
                  // other validations
                  return 'video'
              }
          }
      },
      playerOptions() {
          return (media) => {
              return {
                  height: '360',
                  fluid: true,
                  autoplay: false,
                  muted: true,
                  language: 'en',
                  playbackRates: [0.7, 1.0, 1.5, 2.0],
                  sources: media.sources,
                  poster: media.poster,
              }
          }
      }
  },
  methods: {
    enterChat(post) {
      doAddFriend({
        toId: post.user.id
      }).then(res => {
      }).catch(err => {
      })
      this.$router.push({name: 'Chat', params: {friendId: post.user.id}})
    },
    addContactPerson(friend) {
      doAddFriend({
        toId: friend.userId
      }).then(res => {
      }).catch(err => {
      })
      this.$router.push({name: 'Chat', params: {friendId: friend.userId}})
    },
    closeSidebar() {
      this.isReleaseCard = false;
      this.userPosts = [];
      // 获取帖子信息
      getPostByPage({
        pageNum: 1,
        pageSize: this.pageSize
      }).then(res => {
        for (let i = 0; i < res.data.data.length; i++) {
          // 设置显示评论的条数
          res.data.data.len = 2;
          this.userPosts.push(res.data.data[i])
        }
      }).catch(err => {
        console.log("err = ", err)
      })
    },
    getRandomInt(min, max) {
      return Math.floor(Math.random() * (max - min)) + min;
    },
    // 点击喜欢图标
    isLike(post) {
      post.isLike = !post.isLike;
      if (post.isLike) {
        post.likes = post.likes + 1;
        post.likesUser.push({ username: localStorage.getItem("username"), headImage: localStorage.getItem("headImage") })
      } else {
        post.likes = post.likes - 1;
        post.likesUser.pop()
      }
      doLikePost({
        postId: post.id
      }).then(res => {
        let color = `rgb(${this.getRandomInt(0,255)},${this.getRandomInt(0,255)},${this.getRandomInt(0,255)})`
        this.$vs.notify({ title: '通知', text: post.isLike ? '该帖子已成功点赞' : '点赞取消', color: color, position: 'top-center' })
      }).catch(err => {
        console.log("err = ", err)
      })
    },
    // 加载更多数据
      loadContent() {
          this.$vs.loading({
              background: this.backgroundLoading,
              color: this.colorLoading,
              container: "#button-load-more-posts",
              scale: 0.45
          })
        this.pageNum = this.pageNum + 1;
        getPostByPage({
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }).then(res => {
          if (res.data.data.length === 0) {
            this.$vs.notify({
              title:'温馨提示',
              text: "帖子已经请求到底了",
              color:'warning',
              position:'top-right'})
          } else {
            for (let i = 0; i < res.data.data.length; i++) {
              this.userPosts.push(res.data.data[i]);
            }
          }
          this.$vs.loading.close("#button-load-more-posts > .con-vs-loading")
        }).catch(err => {
          console.log("err = ", err)
          this.$vs.loading.close("#button-load-more-posts > .con-vs-loading")
        })
      },
    // 发表评论
    releaseComment(post) {
      let nowTime = this.dateFormat(new Date());
      if (post.commentbox !== "") {
        doReleaseComment({
          postId: post.id,
          commentContent: post.commentbox,
          releaseDate: nowTime
        }).then(res => {
          if (res.data.code === 200) {
            post.commentList.unshift({ content: post.commentbox, username: localStorage.getItem("username"), headImage: localStorage.getItem("headImage"), createTime: nowTime })
            post.commentbox = "";
            this.$vs.notify({
              title:'提示',
              text: "评论发表成功",
              color:'success',
              position:'top-center'})
          }
        }).catch(err => {
          console.log("err = ", err)
        })
      }
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
      let hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
      let minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    }
  },
  components: {
      videoPlayer,
      AddPostCard
  },
  mounted() {
    this.userInfo.username = localStorage.getItem('username');
    this.userInfo.profileImg = localStorage.getItem('headImage');
    if (this.userInfo.profileImg == "null") {
      this.userInfo.profileImg  = "";
    }
      this.wasSidebarOpen = this.$store.state.reduceButton;
      // this.$store.commit('TOGGLE_REDUCE_BUTTON', true)
    // 获取帖子信息
    getPostByPage({
      pageNum: 1,
      pageSize: this.pageSize
    }).then(res => {
      this.userPosts = []
      for (let i = 0; i < res.data.data.length; i++) {
        // 设置显示评论的条数
        res.data.data.len = 2;
        this.userPosts.push(res.data.data[i])
      }
    }).catch(err => {
      console.log("err = ", err)
    })
    // 获取社区活跃达人
    getActivePerson({
      pageNum: this.activePageNum,
      pageSize: this.activePageSize
    }).then(res => {
      if (res.data.code === 200) {
        this.suggestedFriends = []
        for (let i = 0; i < res.data.data.length; i++) {
          this.suggestedFriends.push(res.data.data[i]);
        }
      }
    }).catch(err => {
      console.log(err)
    })
  },
  beforeDestroy() {
      if (!this.wasSidebarOpen) this.$store.commit('TOGGLE_REDUCE_BUTTON', false)
  },
}
</script>

<style lang="scss">
@import "@/assets/scss/vuesax/pages/profile.scss";
</style>
