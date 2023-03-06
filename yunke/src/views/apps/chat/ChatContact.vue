<!--
  好友列表中每个好友组件
-->
<template>
    <div class="chat__contact flex items-center px-2 py-3" :class="{'bg-primary-gradient text-white shadow-lg': isActiveChatUser}">
        <div class="contact__avatar mr-3">
            <vs-avatar class="border-2 border-solid border-white" :src="contact.headImage" size="42px"></vs-avatar>
        </div>
        <div class="contact__container w-full flex items-center justify-between overflow-hidden">
            <div class="contact__info flex flex-col truncate w-5/6">
                <h5 class="font-semibold" :class="{'text-white': isActiveChatUser}">{{ contact.userName }}</h5>
                <span class="truncate">{{ contact.content }}</span>
            </div>

            <div class="chat__contact__meta flex self-start flex-col items-end w-1/6">
                <span class="whitespace-no-wrap" v-if="lastMessaged != null">{{ lastMessaged | date(true) }}</span>
                <vs-avatar color="primary" :text="`${unseenMsg}`" size="20px" v-if="unseenMsg" />
            </div>
        </div>
      <li class="px-4 flex items-start cursor-pointer hover:text-primary" @click.stop="moveTo()">
        <feather-icon icon="TrashIcon" svg-classes="h-5 w-5"></feather-icon>
      </li>
    </div>
</template>

<script>
    import {doDeleteContactPerson} from "../../../network";

    export default{
        props: {
            contact: {
                type: Object,
                required: true,
            },
            lastMessaged: {
                type: String,
                default: "",
            },
            unseenMsg: {
                type: Number,
                default: 0,
            },
            isActiveChatUser: {
                type: Boolean,
            }
        },
        methods: {
          moveTo() {
            this.openConfirm();
          },
          openConfirm() {
            this.$vs.dialog({
              type: 'confirm',
              color: 'danger',
              title: `确认`,
              text: '您确定要删除好友'+this.contact.userName+'吗？',
              accept: this.acceptAlert,
              acceptText: '确认',
              cancelText: '取消'
            })
          },
          acceptAlert() {
            doDeleteContactPerson({
              toId: this.contact.friendId
            }).then(res => {
              if (res.data.code == 200) {
                this.$emit("deleteFriend", this.contact)
              }
            }).catch(err => {
              console.log(err)
            })
          }
        }
    }
</script>