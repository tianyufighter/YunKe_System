<!--
  信息平台的信息一行显示一个
-->

<template>
  <vx-card class="list-view-item mb-base overflow-hidden">

    <template slot="no-body">
      <div class="vx-row item-details no-gutter">

        <!-- IMG COL -->
        <div class="vx-col sm:w-1/4 w-full item-img-container bg-white flex items-center justify-center">
          <img
              :src="item.articleCover ? item.articleCover : require(`@/assets/images/pages/404.png`)"
              alt="图片"
              class="grid-view-img p-4">
        </div>

        <!-- ITEM NAME & DESC COL -->
        <div class="vx-col sm:w-1/2">
          <div class="p-4 pt-6">
            <slot name="item-meta">
              <h6 class="item-name font-semibold mb-1">{{item.articleTitle}}</h6>
              <p class="text-sm mb-4 cursor-pointer" @click="enterChat">发布者: <span class="text-primary font-semibold">{{item.username}}</span></p>
              <p class="item-description text-sm">{{item.articleText}}</p>
            </slot>
          </div>
        </div>

        <!-- PURCHASE COL -->
        <div class="vx-col sm:w-1/4 w-full flex items-center">

          <div class="p-4 flex flex-col w-full border border-solid d-theme-border-grey-light border-r-0 border-t-0 border-b-0">

            <div class="bg-primary flex self-end text-white py-1 px-2 rounded">
              <span class="text-sm mr-2">{{item.commentCount}}条评论</span>
              <feather-icon icon="MessageSquareIcon" svgClasses="h-4 w-4" />
            </div>

            <div class="my-6">
              <h5 class="font-bold text-center">标签: {{item.category}}</h5>
              <span class="text-grey flex items-start justify-center mt-1">
                <span class="text-sm ml-2">{{item.createTime}}</span>
              </span>
            </div>

            <div style="cursor:pointer;" class="bg-primary flex self-end text-white py-1 px-2 rounded" @click="showBlogDetail">
              <span class="text-sm mr-2">详情</span>
            </div>
          </div>
        </div>
      </div>
    </template>

  </vx-card>
</template>

<script>
import {doAddFriend} from "../../network";
import {BCard, BLink, BImg, BCardBody, BCardTitle, BMedia, BMediaAside, BMediaBody, BBadge, BCardText, BAvatar} from "bootstrap-vue";

export default{
    props: {
        item: {
            type: Object,
            required: true
        }
    },
    components: {
      BCard, BLink, BImg, BCardBody, BCardTitle, BMedia, BMediaAside, BMediaBody, BBadge, BCardText,BAvatar
    },
    methods: {
      showBlogDetail() {
        this.$router.push({
          name: 'BlogDetails',
          params: {id: this.item.id}
        })
      },
      enterChat() {
        doAddFriend({
          toId: this.item.userId
        }).then(res => {
        }).catch(err => {
        })
        this.$router.push({name: 'Chat', params: {friendId: this.item.userId}})
      },
      toggleItemInWishList(item) {
          this.$store.dispatch('eCommerce/toggleItemInWishList', item)
      },
      additemInCart(item) {
          this.$store.dispatch('eCommerce/additemInCart', item)
      },
      cartButtonClicked(item) {
          if(this.isInCart(item.objectID)) this.$router.push('/apps/eCommerce/checkout')
          else this.additemInCart(item)
      }
    }
}
</script>

<style lang="scss" scoped>
.list-view-item {
    .item-name, .item-description {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    .item-name {
        -webkit-line-clamp: 2;
    }

    .item-description {
        -webkit-line-clamp: 5;
    }

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
