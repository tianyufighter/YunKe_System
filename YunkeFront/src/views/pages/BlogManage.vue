<!--
  信息管理界面
-->
<template>
  <div id="data-list-list-view" class="data-list-container">

    <vs-table ref="table" multiple v-model="selected" pagination :max-items="itemsPerPage" search :data="blogList">

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
            <span>请选择博客的状态</span>
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
        </div>

        <!-- ITEMS PER PAGE -->
        <vs-dropdown vs-trigger-click class="cursor-pointer mb-4 mr-4">
          <div class="p-4 border border-solid d-theme-border-grey-light rounded-full d-theme-dark-bg cursor-pointer flex items-center justify-between font-medium">
            <span class="mr-2">{{ currentPage * itemsPerPage - (itemsPerPage - 1) }} - {{ blogList.length - currentPage * itemsPerPage > 0 ? currentPage * itemsPerPage : blogList.length }} of {{ blogList.length }}</span>
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
        <vs-th>标题</vs-th>
        <vs-th>内容</vs-th>
        <vs-th>标签</vs-th>
        <vs-th>状态</vs-th>
        <vs-th>合规状态</vs-th>
        <vs-th>发表时间</vs-th>
        <vs-th>查看详情</vs-th>
      </template>

      <template slot-scope="{data}">
          <tbody>
            <vs-tr :data="tr" :key="tr.id" v-for="tr in data">

              <vs-td>
                <p class="product-name font-medium">{{ tr.articleTitle }}</p>
              </vs-td>

              <vs-td>
                <p class="product-category">{{ tr.articleText }}</p>
              </vs-td>
              <vs-td>
                <p class="product-price">{{ tr.category}}</p>
              </vs-td>

              <vs-td>
                <vs-chip :color="getStatusColor(tr.status)" class="product-order-status">{{ blogStatus(tr.status) }}</vs-chip>
              </vs-td>

              <vs-td>
                <vs-chip :color="getViolationColor(tr.violation)" class="product-order-status">{{ violationStatus(tr.violation) }}</vs-chip>
              </vs-td>

              <vs-td>
                <p class="product-price">{{ tr.createTime.replace("T", " ")}}</p>
              </vs-td>
              <vs-td>
                <vs-button color="primary" type="filled" @click.stop="showBlogDetail(tr)">详情</vs-button>
              </vs-td>
            </vs-tr>
          </tbody>
        </template>
    </vs-table>
    <vs-popup fullscreen class="holamundo" title="博客详情" :active.sync="popupActive">
      <div style="display: flex; justify-content: space-around">
        <div>
          <label class="text-primary font-bold">博客标题</label>
          <vs-input v-model="articleTitle"/>
        </div>
        <div>
          <label class="text-primary font-bold">博客标签</label>
          <vs-input v-model="category"/>
        </div>
      </div>
      <br/>
      <mavon-editor
          ref="md"
          placeholder="请输入文章内容..."
          :boxShadow="false"
          style="z-index: 1; border: 1px solid #d9d9d9; height: 66vh;"
          v-model="articleText"
          :external-link="externalLink"
          :toolbars="toolbars"
          code-style="atom-one-dark"
          :ishljs="true"
      >
      </mavon-editor>
      <br/>
      <div style="display: flex; justify-content: space-between">
        <span></span>
        <vs-button color="success" type="filled" @click="updateBlog">更改</vs-button>
      </div>
    </vs-popup>
  </div>
</template>

<script>
import AddNewDataSidebar from './AddNewDataSidebar.vue';
import {getMyBlogByCondition, getMyAllBlog, updateBlog} from "@network/blog";
import {BModal, BForm, BFormGroup, BFormInput, BFormRadioGroup} from "bootstrap-vue";
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
    AddNewDataSidebar,
    BModal, BForm, BFormGroup, BFormInput, BFormRadioGroup
  },
  data() {
    return {
      selected: [],
      users: [],
      itemsPerPage: 3,
      popupActive: false,
      isMounted: false,
      addNewDataSidebar: false,
      activePrompt:false,
      post: [],
      val:'发布中',
      order_status_choices: [
        {text:'公开',value: 1},
        {text:'私密',value: 0},
      ],
      externalLink: {
        hljs_js: () => '/md/highlightjs/highlight.min.js',
        hljs_css: (css) => '/md/highlightjs/styles/' + css + '.min.css',
        hljs_lang: (lang) => '/md/highlightjs/languages/' + lang + '.min.js',
        katex_css: () => '/md/katex/katex.min.css',
        katex_js: () => '/md/katex/katex.min.js',
        markdown_css: () => '/md/markdown/github-markdown.min.css'
      },
      //参数
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: false, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true // 预览
      },
      articleText: null,
      articleTitle: null,
      category: null,
      blogList: [],
      blog: null,
    }
  },
  computed: {
    currentPage() {
      if(this.isMounted) {
        return this.$refs.table.currentx
      }
      return 0
    },
    violationStatus() {
      return (val) => {
        if (val ==  0) {
          return "正常";
        } else if (val == 1) {
          return "违规";
        } else if (val == 2) {
          return "待审核";
        } else {
          return "未知";
        }
      }
    },
    blogStatus() {
      return (val) => {
        if (val == 1) {
          return "公开";
        } else {
          return '私密';
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
    updateBlog() {
      let data = {
        id: this.blog.id,
        articleTitle: this.articleTitle,
        category: this.category,
        articleText: this.$refs.md.d_value,
        articleContent: this.$refs.md.d_render,
        violation: this.blog.violation == 1 ? 2 : null
      }
      updateBlog(data).then(res => {
        if (res.data.code == 200) {
          this.popupActive = false;
          getMyAllBlog().then(res => {
            if (res.data.code == 200) {
              this.blogList = res.data.data;
            }
          }).catch(err => {
            this.$vs.notify({
              color:'danger',
              title:'错误提示',
              text:'查询博客信息失败!'
            })
          })
          this.$vs.notify({
            color:'success',
            title:'消息提示',
            text:'更新博客信息成功!'
          })
        }
      }).catch(err => {
        this.$vs.notify({
          color:'danger',
          title:'错误提示',
          text:'更新博客信息失败!'
        })
      })
    },
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
    showBlogDetail(data) {
      this.popupActive=true;
      this.blog = data;
      this.articleText = data.articleText;
      this.articleTitle = data.articleTitle;
      this.category = data.category;
    },
    getStatusColor(status) {
      if(status == '1') return "primary"
      else return "success";
    },
    getViolationColor(num) {
      if(num == 0) return "success"
      if(num == 1) return "danger"
      if (num == 2) return "warning"
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
    getMyAllBlog().then(res => {
      if (res.data.code == 200) {
        this.blogList = res.data.data;
      }
    }).catch(err => {
      this.$vs.notify({
        color:'danger',
        title:'错误提示',
        text:'查询博客信息失败!'
      })
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
