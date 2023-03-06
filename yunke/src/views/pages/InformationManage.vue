<!--
  信息管理界面
-->
<template>
  <div id="data-list-list-view" class="data-list-container">

    <add-new-data-sidebar :isSidebarActive="addNewDataSidebar" @closeSidebar="closeSidebar" />

    <vs-table ref="table" multiple v-model="selected" pagination :max-items="itemsPerPage" search :data="post">

      <div slot="header" class="flex flex-wrap-reverse items-center flex-grow justify-between">
        <vs-prompt
            vsTitle="状态修改"
            vsAcceptText="更新"
            vsCancelText="取消"
            @vs-cancel="val=''"
            @vs-accept="updateLabel"
            @vs-close="close"
            :vs-active.sync="activePrompt">
          <div class="con-exemple-prompt">
            <span>请选择信息的状态</span>
            <vs-select v-model="val" class="mt-5 w-full">
              <vs-select-item :key="item.value" :value="item.value" :text="item.text" v-for="item in order_status_choices" />
            </vs-select>
          </div>
        </vs-prompt>

        <div class="flex flex-wrap-reverse items-center">

          <!-- ACTION - DROPDOWN -->
          <vs-dropdown vs-trigger-click class="cursor-pointer mr-4 mb-4">

            <div class="p-4 shadow-drop rounded-lg d-theme-dark-bg cursor-pointer flex items-center justify-center text-lg font-medium w-32">
              <span class="mr-2">操作</span>
              <feather-icon icon="ChevronDownIcon" svgClasses="h-4 w-4" />
            </div>

            <vs-dropdown-menu>

              <vs-dropdown-item>
                <span @click="clickDelete">删除</span>
              </vs-dropdown-item>
              <vs-dropdown-item>
                <span @click="clickUpdate" >更新状态</span>
              </vs-dropdown-item>
            </vs-dropdown-menu>
          </vs-dropdown>

          <!-- ADD NEW -->
          <div class="p-3 mb-4 mr-4 rounded-lg cursor-pointer flex items-center justify-between text-lg font-medium text-base text-primary border border-solid border-primary" @click="addNewDataSidebar = true">
              <feather-icon icon="PlusIcon" svgClasses="h-4 w-4" />
              <span class="ml-2 text-base text-primary">发布消息</span>
          </div>
        </div>

        <!-- ITEMS PER PAGE -->
        <vs-dropdown vs-trigger-click class="cursor-pointer mb-4 mr-4">
          <div class="p-4 border border-solid d-theme-border-grey-light rounded-full d-theme-dark-bg cursor-pointer flex items-center justify-between font-medium">
            <span class="mr-2">{{ currentPage * itemsPerPage - (itemsPerPage - 1) }} - {{ post.length - currentPage * itemsPerPage > 0 ? currentPage * itemsPerPage : post.length }} of {{ post.length }}</span>
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
        <vs-th sort-key="name">标题</vs-th>
        <vs-th sort-key="category">类型</vs-th>
        <vs-th sort-key="popularity">状态</vs-th>
        <vs-th sort-key="order_status">发表时间</vs-th>
      </template>

      <template slot-scope="{data}">
          <tbody>
            <vs-tr :data="tr" :key="tr.id" v-for="tr in data">

              <vs-td>
                <p class="product-name font-medium">{{ tr.infoTitle }}</p>
              </vs-td>

              <vs-td>
                <p class="product-category">{{ infoType(tr.type) }}</p>
              </vs-td>

              <vs-td>
                <vs-chip :color="getOrderStatusColor(tr.status)" class="product-order-status">{{ infoStatus(tr.status) }}</vs-chip>
              </vs-td>

              <vs-td>
                <p class="product-price">{{ tr.createTime.replace("T", " ")}}</p>
              </vs-td>

            </vs-tr>
          </tbody>
        </template>
    </vs-table>
  </div>
</template>

<script>
import AddNewDataSidebar from './AddNewDataSidebar.vue';
import {
  doDeleteInfo,
  doDeleteMultiPosts,
  doUpdateInfoStatus,
  getMyReleaseInfo,
  getPersonalPostByNum,
  getPostByPage
} from "../../network";

export default {
  components: {
    AddNewDataSidebar
  },
  data() {
    return {
      selected: [],
      users: [],
      itemsPerPage: 2,
      isMounted: false,
      addNewDataSidebar: false,
      activePrompt:false,
      post: [],
      val:'发布中',
      order_status_choices: [
        {text:'发布中',value:'发布中'},
        {text:'已完成',value:'已完成'},
        {text:'取消',value:'取消'},
      ],
    }
  },
  computed: {
    currentPage() {
      if(this.isMounted) {
        return this.$refs.table.currentx
      }
      return 0
    },
    infoType() {
      return (val) => {
        if (val ==  1) {
          return "二手商品";
        } else if (val == 2) {
          return "失物招领";
        } else if (val == 3) {
          return "兼职信息";
        } else {
          return "其它";
        }
      }
    },
    infoStatus() {
      return (val) => {
        if (val == 1) {
          return "发布中";
        } else if (val == 2) {
          return "已完成";
        } else if (val == 3) {
          return "取消";
        } else {
          return "未知";
        }
      }
    },
    infoStatusNum() {
      return (val) => {
        if (val == '发布中'){
          return 1;
        } else if (val == '已完成') {
          return 2;
        } else if (val == '取消') {
          return 3;
        } else {
          return 0;
        }
      }
    }
  },
  methods: {
    closeSidebar() {
      // 获取发布的信息
      getMyReleaseInfo().then(res => {
        this.addNewDataSidebar = false;
        this.post = [];
        for (let i = 0; i < res.data.data.length; i++) {
          this.post.push(res.data.data[i])
        }
      }).catch(err => {
      })
    },
    getOrderStatusColor(status) {
      if(status == '1') return "warning"
      if(status == '2') return "success"
      if(status == '3') return "danger"
      return "primary"
    },
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
        text: '您确定要删除这些数据吗？',
        accept: this.acceptAlert,
        acceptText: '确认',
        cancelText: '取消'
      })
    },
    acceptAlert() {
      let idList = [];
      for (let i = 0; i < this.selected.length; i++) {
        idList.push(this.selected[i].id)
      }
      doDeleteInfo({
        idList: idList
      }).then(res => {
        if (res.data.code === 200) {
          this.selected = []
          getMyReleaseInfo().then(res => {
            if (res.data.code === 200) {
              this.post = []
              for (let i = 0; i < res.data.data.length; i++) {
                this.post.push(res.data.data[i]);
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
    clickDelete() {
      this.openConfirm();
    },
    clickUpdate() {
      this.activePrompt = true;
    },
    updateLabel(){
      let idList = [];
      for (let i = 0; i < this.selected.length; i++) {
        idList.push(this.selected[i].id)
      }
      doUpdateInfoStatus({
        idList: idList,
        infoStatus: this.infoStatusNum(this.val)
      }).then(res => {
        if (res.data.code === 200) {
          this.selected = []
          getMyReleaseInfo().then(res => {
            if (res.data.code === 200) {
              this.post = []
              for (let i = 0; i < res.data.data.length; i++) {
                this.post.push(res.data.data[i]);
              }
              this.$vs.notify({
                color:'success',
                title:'通知',
                text:'消息状态更新成功'
              })
            }
          }).catch(err => {
          })
        }
      }).catch(err => {
        console.log("err = ", err)
      })
    },
    close(){
      this.$vs.notify({
        color:'danger',
        title:'已关闭',
        text:'你关闭了弹窗!'
      })
    },
  },
  mounted() {
    this.isMounted = true;
  },
  created() {
    getMyReleaseInfo().then(res => {
      console.log(res)
      for (let i = 0; i < res.data.data.length; i++) {
        this.post.push(res.data.data[i])
      }
    }).catch(err => {
    })
  }
}
</script>

<style lang="scss">
#data-list-list-view {
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
            padding: 20px;
            &:first-child{
              border-top-left-radius: .5rem;
              border-bottom-left-radius: .5rem;
            }
            &:last-child{
              border-top-right-radius: .5rem;
              border-bottom-right-radius: .5rem;
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
