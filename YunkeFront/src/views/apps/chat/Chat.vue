<!--
  聊天板块
-->
<template>
    <div @click="hideElement" id="chat-app" class="border border-solid d-theme-border-grey-light rounded relative overflow-hidden">
        <vs-sidebar class="items-no-padding" parent="#chat-app" :click-not-close="clickNotClose" :hidden-background="clickNotClose" v-model="isChatSidebarActive" id="chat-list-sidebar">
            <div class="chat__profile-search flex p-4">
                <!--头像-->
                <div class="relative inline-flex">
                    <vs-avatar class="m-0 border-2 border-solid border-white" color="primary" :text="username && username.length > 2 ? username.substr(0, 2) : username" :src="headImage" size="40px" />
                    <div class="h-3 w-3 border-white border border-solid rounded-full absolute right-0 bottom-0" :class="'bg-success'"></div>
                </div>
                <!--好友搜索-->
                <vs-input icon="icon-search" icon-pack="feather" class="w-full mx-5 input-rounded-full no-icon-border" placeholder="搜索好友" v-model="searchQuery"/>
            </div>

            <vs-divider class="d-theme-border-grey-light m-0" />
            <VuePerfectScrollbar class="chat-scroll-area pt-4" :settings="settings">

                <!-- 正在聊天列表 -->
                <div class="chat__chats-list mb-8" v-if="this.chatting.length != 0">
                    <h3 class="text-primary mb-5 px-4">正在聊天</h3>
                    <ul class="chat__active-chats bordered-items">
                        <li class="cursor-pointer" v-for="contact in chatting" :key="contact.friendId" @click="updateActiveChatUser(contact)">
                            <chat-contact @deleteFriend="deleteFriend" :friendImg="contact.headImage" :contact="contact" :lastMessaged="contact.sendTime" :unseenMsg="contact.notReadNum" :isActiveChatUser="isActiveChatUser(contact)"></chat-contact>
                        </li>
                    </ul>
                </div>


                <!-- 好友列表 -->
                <div class="chat__contacts">
                    <h3 class="text-primary mb-5 px-4">好友列表</h3>
                    <ul class="chat__contacts bordered-items">
                        <li class="cursor-pointer" v-for="contact in filteredFaq" :key="contact.friendId" @click="updateActiveChatUser(contact)">
                            <chat-contact @deleteFriend="deleteFriend" :contact="contact" :unseenMsg="contact.notReadNum" :friendImg="contact.headImage"></chat-contact>
                        </li>
                    </ul>
                </div>
            </VuePerfectScrollbar>
        </vs-sidebar>

        <!-- RIGHT COLUMN -->
        <div class="chat__bg app-fixed-height chat-content-area border border-solid d-theme-border-grey-light border-t-0 border-r-0 border-b-0" :class="{'sidebar-spacer--wide': clickNotClose, 'flex items-center justify-center': activeChatUser === null}">
            <template v-if="activeChatUser">
                <div class="chat__navbar">
                    <chat-navbar :isSidebarCollapsed="!clickNotClose" :friend="activeChatUser" :isPinnedProp="isChatPinned" @openContactsSidebar="toggleChatSidebar(true)" @showProfileSidebar="updateUserProfileId" @toggleIsChatPinned="toggleIsChatPinned"></chat-navbar>
                </div>
                <VuePerfectScrollbar class="chat-content-scroll-area border border-solid d-theme-border-grey-light" :settings="settings" ref="chatLogPS">
                    <div class="chat__log" ref="chatLog">
                        <chat-log :friend="activeChatUser" v-if="activeChatUser" ref="chatMessage"></chat-log>
                    </div>
                </VuePerfectScrollbar>
                <VEmojiPicker :i18n="emojiI18N" :style="{position: 'absolute', bottom: '60px'}" v-show="isShowEmoji" @select="selectEmoji"></VEmojiPicker>
                <div class="chat__input flex p-4 bg-white">
                  <feather-icon @click.stop="isShowEmoji = !isShowEmoji" icon="SmileIcon" class="text-primary mr-4" style="cursor: pointer"></feather-icon>
                  <vs-input id="textarea" class="flex-1" placeholder="输入消息" v-model="typedMessage" @keyup.enter="sendMsg" />
                  <vs-button class="bg-primary-gradient ml-4" type="filled" @click="sendMsg">发送</vs-button>
                </div>
            </template>
            <template v-else>
                <div class="flex flex-col items-center">
                    <feather-icon icon="MessageSquareIcon" class="mb-4 bg-white p-8 shadow-md rounded-full" svgClasses="w-16 h-16"></feather-icon>
                    <h4 class=" py-2 px-4 bg-white shadow-md rounded-full cursor-pointer" @click.stop="toggleChatSidebar(true)">开始聊天</h4>
                </div>
            </template>
        </div>
    </div>
</template>

<script>
import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import contacts from './contacts'
import ChatContact from "./ChatContact.vue"
import ChatNavbar from './ChatNavbar.vue'
import ChatLog from './ChatLog.vue'
import {doUpdateMessageStatus, getAllContactPerson} from "../../../network";
import {VEmojiPicker} from "v-emoji-picker";

const i18n = {
  search: '搜索...',
  categories: {
    Activity: "活动",
    Flags: "旗帜",
    Foods: "食物",
    Frequently: "常用",
    Objects: "物品",
    Nature: "自然",
    Peoples: "任务",
    Symbols: "符号",
    Places: "地理"
  }
}

export default{
    name: 'vx-sidebar',
    data() {
        return {
          wsUrl: 'ws://162.14.77.43:8005/conversation/chat',
          // wsUrl: 'ws://localhost:8005/conversation/chat',
          headImage: '',
          username: '',
          websocket: null,
          active: true,
          isHidden: false,
          contacts: contacts,
          searchContact: "",
          activeProfileSidebar: false,
          activeChatUser: null, // 正在聊天的好友id
          userProfileId: -1,
          typedMessage: "", // 输入框的聊天信息
          isShowEmoji: false,
          isChatPinned: false,
          settings: {
              maxScrollbarLength: 60,
              wheelSpeed: 0.70,
          },
          clickNotClose: true,
          isChatSidebarActive: true,
          windowWidth: window.innerWidth,
          // 好友列表所有的聊天信息
          chatting: [],
          friendList: [
            // {
            //   friendId: 1,
            //   userName: '张三',
            //   content: '收到张三的新消息',
            //   headImage: 'https://static.kurihada.com/yunke/profile0.jpg',
            //   sendTime: '2022-04-12 02:35:13',
            //   notReadNum: 1
            // },
          ],
          searchQuery: '', // 搜索框的内容
          emojiI18N: i18n
        }
    },
    computed: {
        // 是否是被点击的用户
        isActiveChatUser() {
            return (contact) => this.activeChatUser !== null ? contact.friendId == this.activeChatUser.friendId : false
        },
        filteredFaq() {
          return this.friendList.filter((faq) => {
            if (faq.userName.includes(this.searchQuery)) {
              return true;
            } else {
              return false;
            }
          });
        }
    },
    watch: {
      // 监听点击的当前好友
      activeChatUser(newVal, oldVal) {
        // console.log("我被触发了")
      }
    },
    methods: {
      deleteFriend(friend) {
        this.friendList = this.friendList.filter((friendItem) => {
          if (friendItem.friendId == friend.friendId) return false;
          else return true;
        })
        this.chatting = this.chatting.filter((friendItem) => {
          if (friendItem.friendId == friend.friendId) return false;
          else return true;
        })
        if (this.activeChatUser.friendId == friend.friendId) {
          this.activeChatUser = null;
        }
        this.$vs.notify({
          color: 'danger',
          title: '删除通知',
          text: '好友'+friend.userName + '已被删除',
          position:'top-left'
        })
      },
      closeProfileSidebar(value) {
            this.activeProfileSidebar = value
        },
      updateUserProfileId(userId) {
          this.userProfileId = userId;
          this.activeProfileSidebar = !this.activeProfileSidebar;
      },
      updateActiveChatUser(contact) {
          this.activeChatUser = contact
          this.activeChatUser.notReadNum = null;
          this.toggleChatSidebar();
          this.typedMessage = '';
      },
      /**
       * 发送消息
       */
      sendMsg() {
          if(!this.typedMessage) return
          // 更新左侧好友列表对应好友的最新消息
          this.activeChatUser.content = this.typedMessage;
          let releaseTime = this.dateFormat(new Date());
          this.websocketOnSend({
            toUserId: this.activeChatUser.friendId,
            message: this.typedMessage,
            sendTime: releaseTime
          })
          this.$refs.chatMessage.chatData.push({
            content: this.typedMessage,
            receiveId: this.activeChatUser.friendId,
            sendId: parseInt(localStorage.getItem("userId")),
            releaseTime: releaseTime
          });
          this.typedMessage = '';
        this.$refs.chatLogPS.$el.scrollTop = this.$refs.chatLog.scrollHeight;
        for (let i = 0; i < this.chatting.length; i++) {
            if (this.chatting[i].friendId == this.activeChatUser.friendId) {
              return;
            }
        }
          this.chatting.push(this.activeChatUser);
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
      },
      toggleIsChatPinned(value) {
          this.isChatPinned = value;
      },
      handleWindowResize(event) {
          this.windowWidth = event.currentTarget.innerWidth;
          this.setSidebarWidth();
      },
      setSidebarWidth() {
          if(this.windowWidth < 1200) {
              this.isChatSidebarActive = this.clickNotClose = false;
          }else {
              this.isChatSidebarActive = this.clickNotClose = true;
          }
      },
      toggleChatSidebar(value = false) {
          if(!value && this.clickNotClose) return
          this.isChatSidebarActive = value;
      },
      // 初始化websocket
      initWebSocket() {
        if (typeof WebSocket === 'undefined') {
          return console.log("你的浏览器不支持websocket")
        }
        this.websocket = new WebSocket(this.wsUrl + "/" + localStorage.getItem("userId"),[localStorage.getItem("token")]);
        this.websocket.onmessage = this.websocketOnMessage;
        this.websocket.onopen = this.websocketOnOpen;
        this.websocket.onerror = this.websocketOnError;
        this.websocket.onclose = this.websocketOnClose;
      },
      websocketOnMessage(e) {
        // 接收服务器返回的信息
        let data = JSON.parse(e.data);
        if (data.isSystem) {
          // 系统消息
          console.log("接收到系统消息", data.message)
        } else {
          if (this.activeChatUser != null && data.fromUserId == this.activeChatUser.friendId) {
            if (data.toUserId != data.fromUserId) {
              this.$refs.chatMessage.chatData.push({
                content: data.message,
                receiveId: data.toUserId,
                sendId: data.fromUserId,
                releaseTime: data.sendTime.replace("T", " ")
              });
            }
          } else {
            // 更新消息状态
            doUpdateMessageStatus({
              toId: data.fromUserId,
              time: data.sendTime.replace("T", " ")
            }).then(res => {
              for (let i = 0; i < this.friendList.length; i++) {
                if (this.friendList[i].friendId == data.fromUserId) {
                  this.friendList[i].notReadNum = this.friendList[i].notReadNum + 1;
                  this.friendList[i].content = data.message;
                  this.friendList[i].sendTime = data.sendTime.replace("T", " ");
                  break;
                }
              }
              // console.log("消息重置成功", res)
            }).catch(err => {
              // console.log("消息重置失败", err)
            })
          }
        }
      },
      websocketOnOpen() {
        console.log("连接成功")
      },
      websocketOnError() {
        // 连接建立失败
        // setTimeout(() => {
        //   this.initWebSocket()
        // }, 2000)
      },
      websocketOnClose(e) {
        // console.log('断开连接', e)
        // // 关闭
        // // 离开路由之后断开websocket连接
        // this.websocket.onclose();
      },
      websocketOnSend(data) {
          this.websocket.send(JSON.stringify(data))
      },
      // 表情输入
      selectEmoji(emoji) {// 选择emoji后调用的函数
        let elInput = document.getElementById('textarea')//获取输入框元素
        // console.log(elInput);
        let start = elInput.selectionStart // 记录光标开始的位置
        let end = elInput.selectionEnd // 记录选中的字符 最后的字符的位置
        if (start === undefined || end === undefined) return
        let txt = elInput.value
        // 将表情添加到选中的光标位置
        let result =
            txt.substring(0, start) + emoji.data + txt.substring(end)
        elInput.value = result // 赋值给input的value
        // 重置光标位置
        elInput.focus()
        elInput.selectionStart = start + emoji.data.length
        elInput.selectionEnd = start + emoji.data.length
        this.typedMessage= result // 赋值(注意这里一定要赋值给表情输入框绑定的那个值)
      },
      hideElement(event) {
        if (event.target.classList.contains('emoji')) {
          // 如果点击的是表情库，则不进行任何操作
        } else {
          this.isShowEmoji = false;
        }
      }
    },
    components: {
        VuePerfectScrollbar,
        ChatContact,
        ChatNavbar,
        ChatLog,
        VEmojiPicker
    },
    created() {
      this.$nextTick(() => {
          window.addEventListener('resize', this.handleWindowResize);
      })
      this.setSidebarWidth();
    },
    mounted() {
      this.username = localStorage.getItem("username");
      this.headImage = localStorage.getItem("headImage");
      if (this.headImage == "null") {
        this.headImage = "";
      }
      this.initWebSocket()
      // 获取个人好友列表
      getAllContactPerson().then(res => {
        console.log("好友信息: ", res)
        this.friendList = [];
        if (res.data.code === 200) {
          for (let i = 0; i < res.data.data.length; i++) {
            this.friendList.push(res.data.data[i]);
          }
          if (this.$route.params.friendId) {
            for (let i = 0; i < res.data.data.length; i++) {
              if(res.data.data[i].friendId == this.$route.params.friendId) {
                this.activeChatUser = res.data.data[i];
                this.chatting.push(res.data.data[i]);
                break;
              }
            }
          }
        }
      }).catch(err => {
        console.log("错误信息: ", err)
      })
    },
    beforeDestroy: function () {
        // 离开路由之后断开websocket连接
        this.websocket.onclose();
        window.removeEventListener('resize', this.handleWindowResize);
    }
}
</script>

<style lang="scss">
@import "@/assets/scss/vuesax/apps/chat.scss";
</style>
