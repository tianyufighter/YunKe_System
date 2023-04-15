<!--
  我的帖子界面
 -->
<template>
  <div id="data-list-thumb-view" class="data-list-container">
    <vs-table ref="table" multiple v-model="selected" pagination :max-items="itemsPerPage" search :data="posts">

      <div slot="header" class="flex flex-wrap-reverse items-center flex-grow justify-between">

        <div class="flex flex-wrap-reverse items-center">

          <!-- ACTION - DROPDOWN -->
          <vs-dropdown vs-trigger-click class="cursor-pointer mr-4 mb-4">

            <div class="p-4 shadow-drop rounded-lg d-theme-dark-bg cursor-pointer flex items-center justify-center text-lg font-medium w-32">
              <span class="mr-2">操作</span>
              <feather-icon icon="ChevronDownIcon" svgClasses="h-4 w-4" />
            </div>

            <vs-dropdown-menu>

              <vs-dropdown-item>
                <span @click="deletePosts">删除</span>
              </vs-dropdown-item>

            </vs-dropdown-menu>
          </vs-dropdown>
        </div>

        <!-- ITEMS PER PAGE -->
        <vs-dropdown vs-trigger-click class="cursor-pointer mb-4 mr-4">
          <div class="p-4 border border-solid d-theme-border-grey-light rounded-full d-theme-dark-bg cursor-pointer flex items-center justify-between font-medium">
            <span class="mr-2">{{ currentPage * itemsPerPage - (itemsPerPage - 1) }} - {{ posts.length - currentPage * itemsPerPage > 0 ? currentPage * itemsPerPage : posts.length }} of {{ posts.length }}</span>
            <feather-icon icon="ChevronDownIcon" svgClasses="h-4 w-4" />
          </div>
          <!-- <vs-button class="btn-drop" type="line" color="primary" icon-pack="feather" icon="icon-chevron-down"></vs-button> -->
          <vs-dropdown-menu>

            <vs-dropdown-item @click="itemsPerPage=4">
              <span>4</span>
            </vs-dropdown-item>
            <vs-dropdown-item @click="itemsPerPage=10">
              <span>10</span>
            </vs-dropdown-item>
            <vs-dropdown-item @click="itemsPerPage=15">
              <span>15</span>
            </vs-dropdown-item>
            <vs-dropdown-item @click="itemsPerPage=20">
              <span>20</span>
            </vs-dropdown-item>
          </vs-dropdown-menu>
        </vs-dropdown>
      </div>

      <template slot="thead">
        <vs-th>图片</vs-th>
        <vs-th sort-key="name">标题</vs-th>
        <vs-th sort-key="category">内容</vs-th>
        <vs-th sort-key="popularity">点赞量</vs-th>
        <vs-th sort-key="order_status">发表时间</vs-th>
      </template>

      <template slot-scope="{data}">
        <tbody>
          <vs-tr :data="tr" :key="tr.id" v-for="tr in data">
            <vs-td class="img-container">
              <img :src="tr.postCover" class="product-img" />
            </vs-td>

            <vs-td>
              <p class="product-name font-medium">{{ tr.title }}</p>
            </vs-td>

            <vs-td>
              <p class="product-category">{{ tr.content  | truncate(20) | tailing('...') }}</p>
            </vs-td>

            <vs-td>
              <vs-chip color="success" class="product-order-status">{{ tr.likes }}</vs-chip>
            </vs-td>

            <vs-td>
              <vs-chip color="primary" class="product-order-status">{{ tr.createTime.replace("T", " ") }}</vs-chip>
            </vs-td>

          </vs-tr>
        </tbody>
      </template>
    </vs-table>
  </div>
</template>

<script>
import {getPersonalPostByNum} from "../../network";
import {doDeleteMultiPosts} from "../../network"
export default {
  data() {
    return {
      selected: [],
      itemsPerPage: 2,
      isMounted: false,
      posts: [
        // {
        //   id: 1,
        //   postCover: 'https://static.kurihada.com/yunke/profile0.jpg',
        //   title: '我是标题',
        //   content: '这是张三的帖currentPage子,请多多关照',
        //   likes: 20,
        //   createTime: '2022-04-03 21:58:01'
        // },
      ]
    }
  },
  computed: {
    currentPage() {
      if(this.isMounted) {
        return this.$refs.table.currentx
      }
      return 0
    },
  },
  methods: {
    getPopularityColor(num) {
      if(num > 90) return "success"
      if(num >70) return "primary"
      if(num >= 50) return "warning"
      if(num < 50) return "danger"
      return "primary"
    },
    formatData(data) {
      // formats data received from API
      let formattedData = data.map((item) => {
        const fields = item.fields
        let obj = {}
        for (const key of Object.keys(fields)) {
            obj[key] = Number(fields[key].integerValue) || Number(fields[key].doubleValue) || fields[key].stringValue;
        }
        return obj
      });
      return formattedData
    },
    openConfirm() {
      this.$vs.dialog({
        type: 'confirm',
        color: 'danger',
        title: `确认`,
        text: '您确定要删除帖子数据吗？',
        accept: this.acceptAlert
      })
    },
    acceptAlert() {
      let idList = [];
      for (let i = 0; i < this.selected.length; i++) {
        idList.push(this.selected[i].id)
      }
      doDeleteMultiPosts({
        idList: idList
      }).then(res => {
        if (res.data.code === 200) {
          this.selected = []
          getPersonalPostByNum().then(res => {
            if (res.data.code === 200) {
              this.posts = []
              for (let i = 0; i < res.data.data.length; i++) {
                this.posts.push(res.data.data[i]);
              }
              this.$vs.notify({
                color: 'danger',
                title: '删除通知',
                text: '该条数据已成功删除'
              })
            }
          }).catch(err => {
          })
        }
      }).catch(err => {
        console.log("err1 = ", err)
      })
    },
    // 删除帖子
    deletePosts() {
      this.openConfirm();
    }
  },
  mounted() {
    this.isMounted = true;
  },
  created() {
    // 获取个人帖子信息
    getPersonalPostByNum().then(res => {
      if (res.data.code === 200) {
        for (let i = 0; i < res.data.data.length; i++) {
          this.posts.push(res.data.data[i]);
        }
      }
    }).catch(err => {
    })
  }
}
</script>

<style lang="scss">
#data-list-thumb-view {
  .vs-con-table {

    .vs-table--header {
      display: flex;
      flex-wrap: wrap-reverse;
      margin-left: 1.5rem;
      margin-right: 1.5rem;
      > span {
        display: flex;
        flex-grow: 1;
      }

      .vs-table--search{
        padding-top: 0;

        .vs-table--search-input {
          padding: 0.9rem 2.5rem;
          font-size: 1rem;

          &+i {
            left: 1rem;
          }

          &:focus+i {
            left: 1rem;
          }
        }
      }
    }

    .vs-table {
      border-collapse: separate;
      border-spacing: 0 1.3rem;
      padding: 0 1rem;


      tr{
          box-shadow: 0 4px 20px 0 rgba(0,0,0,.05);
          td{
            padding: 10px;
            &:first-child{
              border-top-left-radius: .5rem;
              border-bottom-left-radius: .5rem;
            }
            &:last-child{
              border-top-right-radius: .5rem;
              border-bottom-right-radius: .5rem;
            }
            &.img-container {
              // width: 1rem;
              // background: #fff;

              span {
                display: flex;
                justify-content: center;
              }

              .product-img {
                height: 110px;
              }
            }
          }
          td.td-check{
            padding: 20px !important;
          }
      }
    }

    .vs-table--thead{
      th {
        padding-top: 0;
        padding-bottom: 0;

        .vs-table-text{
          text-transform: uppercase;
          font-weight: 600;
        }
      }
      th.td-check{
        padding: 0 15px !important;
      }
      tr{
        background: none;
        box-shadow: none;
      }
    }

    .vs-table--pagination {
      justify-content: center;
    }
  }
}
</style>
