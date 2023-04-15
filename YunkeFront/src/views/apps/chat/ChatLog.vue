<!--
  聊天面板
-->
<!-- hasSentPreviousMsg -->
<template>
    <div id="component-chat-log" class="m-8" v-if="chatData">
        <div v-for="(msg, index) in chatData" class="msg-grp-container" :key="msg.id">

            <!-- 如果当前消息与前一个消息不是同一天，就画上分割线提示用户 -->
            <template v-if="chatData[index-1]">
                <vs-divider v-if="!isSameDay(msg.releaseTime, chatData[index-1].releaseTime)">
                    <span>{{ toDate(msg.releaseTime) }}</span>
                </vs-divider>
                <div class="spacer mt-8" v-if="!hasSentPreviousMsg(chatData[index-1].sendId, msg.sendId)"></div>
            </template>

            <div class="flex items-start" :class="[{'flex-row-reverse' : (msg.sendId === userId)}]">

                <template v-if="chatData[index-1]">
                    <!--当前消息是否与前一条消息是同一个发送者-->
                    <template v-if="(!hasSentPreviousMsg(chatData[index-1].sendId, msg.sendId) || !isSameDay(msg.releaseTime, chatData[index-1].releaseTime))">
                        <vs-avatar size="40px" class="m-0 flex-shrink-0" :class="msg.sendId === userId ? 'sm:ml-5 ml-3' : 'sm:mr-5 mr-3'" :src="senderImg(msg.sendId === userId)"></vs-avatar>
                    </template>
                </template>

                <template v-if="index === 0">
                    <vs-avatar size="40px" class="m-0 flex-shrink-0" :class="msg.sendId === userId ? 'sm:ml-5 ml-3' : 'sm:mr-5 mr-3'" :src="senderImg(msg.sendId === userId)"></vs-avatar>
                </template>

                <template v-if="chatData[index-1]">
                    <div class="mr-16" v-if="!(!hasSentPreviousMsg(chatData[index-1].sendId, msg.sendId) || !isSameDay(msg.releaseTime, chatData[index-1].releaseTime))"></div>
                </template>

                <div class="msg break-words relative shadow-md rounded py-3 px-4 mb-2 rounded-lg max-w-sm" :class="{'bg-primary-gradient text-white': (msg.sendId === userId), 'border border-solid border-grey-light bg-white': (msg.sendId !== userId)}">
                    <span>{{ msg.content }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import contacts from './contacts'
import {getAllChatMessage} from "../../../network";

export default{
    props: {
      friend: {
            type: Object,
            required: true,
      }
    },
    data() {
        return {
            contacts: contacts,
            chatData: [], // 聊天信息
            userId: '', // 当前登录用户的id
        }
    },
    computed: {
        // 发送者的头像
        senderImg() {
            return (isSentByActiveUser) => {
                if (isSentByActiveUser) return localStorage.getItem("headImage") != "null" ? localStorage.getItem("headImage") : "";
                else return this.friend.headImage != "null" ? this.friend.headImage : "";
            }
        },
        hasSentPreviousMsg() {
            return (last_sender, current_sender) => last_sender === current_sender
        },
    },
    methods: {
        // 判断当前消息与上一条消息是否是同一天
        isSameDay(time_to, time_from) {
          return time_to.substr(0,10) === time_from.substr(0, 10)
          return true;
        },
        toDate(time) {
          let year = time.substr(0, 4);
          let month = time.substr(5, 2);
          let day = time.substr(8, 2);
          return year + '年' + month + '月' + day + '日';
        },
        scrollToBottom() {
            this.$nextTick(() => {
                this.$parent.$el.scrollTop = this.$parent.$el.scrollHeight
            });
        }
    },
    updated() {
        this.scrollToBottom();
    },
    watch: {
      // 监听点击的当前好友
      friend: {
        // 好友id发生变化就会调用这个函数
        handler(friend) {
          // 请求所有的历史信息
          getAllChatMessage({
            toId: friend.friendId
          }).then(res => {
            console.log("所有的聊天信息: ", res)
            this.chatData = []
            for (let i = 0; i < res.data.data.length; i++) {
              this.chatData.push(res.data.data[i]);
            }
          }).catch(err => {
            console.log("错误信息: ", err)
          })
        },
        // 立即处理，进入页面就触发
        immediate: true
      }
    },
    mounted() {
        this.scrollToBottom();
        this.userId = parseInt(localStorage.getItem("userId"));
    }

}
</script>
