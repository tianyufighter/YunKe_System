<!--
  信息平台的网格布局展示
-->
<template>
  <div class="grid-view-item mb-base overflow-hidden">
    <vx-card>
      <div slot="no-body">
        <img :src="item.infoCover ? item.infoCover : require(`@/assets/images/pages/404.png`)" alt="content-img" class="responsive card-img-top">
      </div>
      <h5 class="mb-2">{{item.infoTitle}}</h5>
      <div class="flex justify-between flex-wrap">
        <span class="text-grey">{{item.createTime.substr(0, 10)}}</span>
        <span class="text-grey">{{itemType}}</span>
      </div>
      <p class="text-grey item-description text-base">{{item.infoContent}}</p>
      <div class="flex justify-between flex-wrap">
        <vs-button class="mt-4 shadow-lg" type="gradient" color="#7367F0" gradient-color-secondary="#CE9FFC" @click="enterChat">{{item.username}}</vs-button>
        <vs-button class="mt-4" type="border" color="#b9b9b9">邮箱:{{item.email}}</vs-button>
      </div>
    </vx-card>
  </div>
</template>

<script>
import {doAddFriend} from "../../network";

export default{
  props: {
    item: {
      type: Object,
      required: true
    },
  },
  computed: {
    itemType() {
      if (this.item.type == 1) {
        return "二手商品";
      } else if (this.item.type == 2) {
        return "失物招领";
      } else if (this.item.type == 3) {
        return "兼职信息";
      } else {
        return "无类型";
      }
    },
  },
  methods: {
    enterChat() {
      doAddFriend({
        toId: this.item.userId
      }).then(res => {
      }).catch(err => {
      })
      this.$router.push({name: 'Chat', params: {friendId: this.item.userId}})
    },
  }
}
</script>

<style lang="scss" scoped>
.grid-view-item {
  .grid-view-img {
    max-width: 100%;
    max-height: 100%;
    width: auto;
    transition: .35s;
  }

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0px 4px 25px 0px rgba(0,0,0,.25);

    .grid-view-img{
      opacity: 0.9;
    }
  }
}
</style>