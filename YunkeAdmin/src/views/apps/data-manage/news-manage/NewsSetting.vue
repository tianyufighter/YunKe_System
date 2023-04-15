<!--用户管理界面-->
<template>
  <div>
    <!-- 筛选用户 -->
    <news-setting-filters
      @clickReset="refetchNewsList"
      @clickSearch="getNewsByCondition"
    />

    <!-- 显示用户的列表 -->
    <b-card
      no-body
      class="mb-0"
    >

      <div class="m-2">
        <!-- 表格头部 -->
        <b-row>
          <b-col cols="12" md="8">
            <b-button
                variant="primary"
                @click="doDeleteNews"
            >
              <span class="text-nowrap">删除</span>
            </b-button>
          </b-col>
          <!-- 搜索 -->
          <b-col
            cols="12"
            md="4"
          >
            <div class="d-flex align-items-center justify-content-end">
              <b-form-input
                v-model="searchQuery"
                class="d-inline-block mr-1"
                placeholder="搜索..."
              />
              <b-button
                  variant="primary"
                  @click="showModal"
              >
                <span class="text-nowrap">发布新闻</span>
              </b-button>
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="filterNewsList"
        responsive
        :fields="tableColumns"
        primary-key="id"
        :sort-by.sync="sortBy"
        show-empty
        empty-text="No matching records found"
        :sort-desc.sync="isSortDirDesc"
      >
        <template v-slot:head(#)="data">
          <b-form-checkbox v-model="allchecked" @change="toggleAll" :indeterminate="indeterminate"></b-form-checkbox>
        </template>
        <template v-slot:cell(#)="row">
          <b-form-checkbox @change="checkboxClick(row.item.id)" v-model="isCheckedObj['index'+row.item.id]"></b-form-checkbox>
        </template>
        <!--Column: 新闻标题-->
        <template #cell(newsTitle)="data">
          <b-badge
              pill
              :variant="`light-primary`"
              class="text-capitalize"
          >
            <span v-if="data.item.newsTitle.length > 10">{{data.item.newsTitle | truncate(10) | tailing('...')}}</span>
            <span v-else>{{data.item.newsTitle}}</span>
          </b-badge>
        </template>
        <!--Column: 新闻内容-->
        <template #cell(newsContent)="data">
          <b-badge
              pill
              :variant="`light-dark`"
              class="text-capitalize"
          >
            <span v-if="data.item.newsContent.length > 20">{{data.item.newsContent | truncate(20) | tailing('...')}}</span>
            <span v-else>{{data.item.newsContent}}</span>
          </b-badge>
        </template>
        <!--Column: 目录-->
        <template #cell(tagName)="data">
          <b-badge
              pill
              :variant="`light-info`"
              class="text-capitalize"
          >
            {{data.item.tagName}}
          </b-badge>
        </template>
        <!--Column: 发布时间-->
        <template #cell(createTime)="data">
          <span>{{data.item.createTime.replace("T"," ")}}</span>
        </template>
        <!--Column: 状态-->
        <template #cell(username)="data">
          <b-badge
              pill
              :variant="`light-primary`"
              class="text-capitalize"
          >
            {{data.item.username}}
          </b-badge>
        </template>
        <!--Column: 是否合规-->
        <template #cell(violation)="data">
          <b-badge
              pill
              :variant="`light-${resolveViolationVariant(data.item.violation)}`"
              class="text-capitalize"
          >
            <span v-if="data.item.violation == 0">正常</span>
            <span v-if="data.item.violation == 1">违规</span>
            <span v-if="data.item.violation == 2">待审核</span>
          </b-badge>
        </template>
        <!-- Column: 操作 -->
        <template #cell(actions)="data">
          <b-dropdown
              variant="link"
              no-caret
              :right="$store.state.appConfig.isRTL"
          >

            <template #button-content>
              <feather-icon
                  icon="MoreVerticalIcon"
                  size="16"
                  class="align-middle text-body"
              />
            </template>

            <b-dropdown-item  @click="showModal(data.item)">
              <feather-icon icon="EditIcon" />
              <span class="align-middle ml-50">编辑</span>
            </b-dropdown-item>

            <b-dropdown-item @click="doDeleteNews(data.item)">
              <feather-icon icon="TrashIcon" />
              <span class="align-middle ml-50">删除</span>
            </b-dropdown-item>
          </b-dropdown>
        </template>
      </b-table>
      <div class="mx-2 mb-2">
        <b-row>

          <b-col
            cols="12"
            sm="6"
            class="d-flex align-items-center justify-content-center justify-content-sm-start"
          >
          </b-col>
          <!-- Pagination -->
          <b-col
            cols="12"
            sm="6"
            class="d-flex align-items-center justify-content-center justify-content-sm-end"
          >

            <b-pagination
              v-model="currentPage"
              :total-rows="totalNews"
              :per-page="perPage"
              first-number
              last-number
              class="mb-0 mt-1 mt-sm-0"
              prev-class="prev-item"
              next-class="next-item"
            >
              <template #prev-text>
                <feather-icon
                  icon="ChevronLeftIcon"
                  size="18"
                />
              </template>
              <template #next-text>
                <feather-icon
                  icon="ChevronRightIcon"
                  size="18"
                />
              </template>
            </b-pagination>

          </b-col>

        </b-row>
      </div>
    </b-card>
    <!-- select 2 demo -->
    <b-modal
        ref="modal-select2"
        size="lg"
        title="新闻信息"
        ok-title="更改"
        cancel-title="取消"
        cancel-variant="outline-secondary"
        @cancel=""
        @hidden=""
        @ok="submitNews"
    >
      <b-form>
        <b-form-group
            label="新闻标题"
            label-for="configName"
        >
          <b-form-input
              id="configName"
              v-model="newsTitle"
              trim
          />
        </b-form-group>

        <b-form-group
            label="新闻标签"
            label-for="configKey"
        >
          <b-form-input
              id="configKey"
              v-model="tagName"
              trim
          />
        </b-form-group>
        <b-form-group
            label="新闻内容"
            label-for="remark"
        >
          <!--添加封面图-->
          <div class="border rounded p-2">
            <h4 class="mb-1">
              封面图片
            </h4>
            <b-media
                no-body
                vertical-align="center"
                class="flex-column flex-md-row"
            >
              <b-media-aside>
                <b-img
                    ref="refPreviewEl"
                    :src="imgUrl"
                    height="110"
                    width="170"
                    class="rounded mr-2 mb-1 mb-md-0"
                />
              </b-media-aside>
              <b-media-body>
                <small class="text-muted">要求图像分辨率 800x400，图像大小 10mb。</small>
                <b-card-text class="my-50">
                  <b-link id="blog-image-text">
                    文件名称: {{file ? file.name : ''}}
                  </b-link>
                </b-card-text>
                <div class="d-inline-block">
                  <b-form-file
                      ref="refInputEl"
                      v-model="file"
                      accept=".jpg, .png, .gif"
                      placeholder="选择图片"
                      @input="inputImageRenderer"
                  />
                </div>
              </b-media-body>
            </b-media>
          </div>
        </b-form-group>

        <b-form-group
            label="新闻内容"
            label-for="remark"
        >
          <b-form-textarea
              id="content"
              v-model="newsContent"
              trim
          />
        </b-form-group>

      </b-form>
    </b-modal>
  </div>
</template>

<script>
import {
  BCard, BRow, BCol, BFormInput, BButton, BTable, BMedia, BAvatar, BLink,BFormRadioGroup, BMediaAside, BImg,BMediaBody,BCardText, BFormFile,
  BBadge, BDropdown, BDropdownItem, BPagination, VBModal, BForm, BFormGroup,BFormTextarea,BFormCheckbox,
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import NewsSettingFilters from '@/views/apps/data-manage/news-manage/NewsSettingFilters.vue'
import NewsSettingAddNew from '@/views/apps/data-manage/news-manage/NewsSettingAddNew.vue'
import Ripple from 'vue-ripple-directive'
import {getNews, deleteNews, updateNews, doUploadImage, addNews} from '@/network/newsmanage'

export default {
  components: {
    NewsSettingFilters, NewsSettingAddNew, BFormRadioGroup, BFormTextarea, BFormGroup, BFormCheckbox, BForm, BCard, BRow,
    BCol, BFormInput, BButton, BTable, BMedia, BAvatar, BLink, BBadge, BDropdown, BDropdownItem, BPagination, BMediaAside,
    BImg,BMediaBody,BCardText, BFormFile, vSelect,
  },
  directives: {
    'b-modal': VBModal,
    Ripple,
  },
  data() {
    return {
      selectedSystemInner: 'yes',
      tableColumns: [
        { key: '#', label: '#'},
        { key: 'newsTitle', label: '新闻标题', sortable: true },
        { key: 'newsContent', label: '新闻内容', sortable: true },
        { key: 'tagName', label: '类别', sortable: true, },
        { key: 'createTime', label: '发布时间' },
        { key: 'username', label: '发布者' },
        { key: 'actions', label: '操作' },
      ],
      perPage: 3, // 每页显示的条数
      searchQuery: '', // 搜索所对应的关键字
      totalBlog: 0, // 总的帖子数
      totalNews: 0, // 总的新闻数
      currentPage: 1,
      perPageOptions: [10, 25, 50, 100],
      sortBy: 'id',
      isSortDirDesc: true,
      blogList: null,
      newsList: null,
      isAddNewUserSidebarActive: false,
      post: {}, // 用户点击某个帖子时，就临时存储该帖子的数据
      idArr: [],
      allchecked: false,
      indeterminate: false,
      isCheckedObj: {},
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

      id: null,
      newsTitle: null,
      newsContent: null,
      tagName: null,
      imgUrl: null,
      file: null
    }
  },
  computed: {
    filterNewsList() {
      return this.newsList?.filter(news => {
        return (news.newsTitle?.includes(this.searchQuery) ||
                news.newsContent?.includes(this.searchQuery)
        );
      })
    }
  },
  filters: {
    truncate(value, limit) {
      return value.substring(0, limit);
    },
    tailing(value, tail) {
      return value + tail;
    }
  },
  methods: {
    inputImageRenderer() {
      const file = this.$refs.refInputEl.files[0];
      // 第一步，将图片上传到服务器
      let formData = new FormData();
      formData.append("file", file);
      doUploadImage(formData).then(res => {
        this.imgUrl = res.data.data.url;
      }).catch(err => {
        this.$vs.notify({
          color:'success',
          title:'错误提示',
          text:'图片上传失败'
        })
      })
    },
    doDeleteNews(data) {
      if (data != null) {
        this.idArr.push(data.id);
      }
      deleteNews(this.idArr).then(res => {
        if (res.data.code == 200 ) {
          this.refetchNewsList();
          this.$vs.notify({
            title:'消息提示',
            text:'新闻信息删除成功',
            color:'success',
            position:'top-center'})
        } else {
          this.$vs.notify({
            title:'错误提示',
            text:'新闻信息删除失败',
            color:'danger',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息删除失败',
          color:'danger',
          position:'top-center'})
      })
    },
    changeStatus(val) {
      this.isAddNewUserSidebarActive = val;
    },
    getNewsByCondition(data) {
      this.currentPage = 1;
      this.perPage = 3;
      getNews({
        pageNum: this.currentPage,
        pageSize: this.perPage,
        newsTitle: data.newsTitle,
        newsContent: data.newsContent
      }).then(res => {
        this.newsList = res.data.data.list;
        this.totalNews = res.data.data.total;
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'新闻信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
    // 重新获取所有的新闻信息
    refetchNewsList() {
      getNews({
        pageNum: this.currentPage,
        pageSize: this.perPage
      }).then(res => {
        console.log("新闻信息: ", res)
        this.newsList = res.data.data.list;
        this.totalNews = res.data.data.total;
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'新闻信息获取失败',
          color:'danger',
          position:'top-center'})
      })
    },
    resolveStatusVariant(data) {
      if (data == 1) {
        return 'primary';
      } else {
        return 'secondary';
      }
    },
    resolveViolationVariant(data) {
      if (data == 0) {
        return 'success';
      } else if (data == 1) {
        return 'danger';
      } else {
        return 'warning'
      }
    },
    showModal(data) {
      this.id = data.id;
      this.newsTitle = data.newsTitle;
      this.newsContent = data.newsContent;
      this.tagName = data.tagName;
      this.imgUrl = data.newsCover;
      this.$refs['modal-select2'].show();
    },
    resetForm() {
      this.id = null;
      this.newsTitle = null;
      this.newsContent = null;
      this.tagName = null;
      this.imgUrl = null;
    },
    submitNews() {
      if (this.id == null) {
        addNews({
          newsTitle: this.newsTitle,
          newsContent: this.newsContent,
          tagName: this.tagName,
          newsCover: this.imgUrl
        }).then(res => {
          if (res.data.code == 200) {
            this.$refs['modal-select2'].hide();
            this.resetForm();
            this.refetchNewsList();
            this.$vs.notify({
              title:'信息提示',
              text:'新闻信息添加成功',
              color:'success',
              position:'top-center'})
          }
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'新闻信息添加失败',
            color:'danger',
            position:'top-center'})
        })
      } else {
        updateNews({
          id: this.id,
          newsTitle: this.newsTitle,
          newsContent: this.newsContent,
          tagName: this.tagName,
          newsCover: this.imgUrl
        }).then(res => {
          if (res.data.code == 200) {
            this.$refs['modal-select2'].hide();
            this.resetForm();
            this.refetchNewsList();
            this.$vs.notify({
              title:'信息提示',
              text:'新闻信息更新成功',
              color:'success',
              position:'top-center'})
          }
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'新闻信息更新失败',
            color:'danger',
            position:'top-center'})
        })
      }
    },
    // 点击全选按钮
    toggleAll(checked) {
      this.idArr = []
      this.blogList.forEach(element => {
        if (checked) {
          this.idArr.push(element.id);
        }
        this.isCheckedObj['index' + element.id] = checked;
      });
    },
    // 复选框点击选中与取消
    checkboxClick(id) {
      let index = this.idArr.indexOf(id);
      if (index > -1) {
        this.idArr.splice(index, 1);
      } else {
        this.idArr.push(id);
      }
    }
  },
  mounted() {
    this.isCheckedObj = {};
    // this.refetchBlogList();
    this.refetchNewsList();
  },
  watch: {
    currentPage() {
      this.refetchNewsList()
    },
    idArr: {
      handler(newVal, oldVal) {
        if (newVal.length == 0) {
          this.indeterminate = false;
          this.allchecked = false;
        } else if (newVal.length == this.blogList.length) {
          this.indeterminate = false;
          this.allchecked = true;
        } else {
          this.indeterminate = true;
          this.allchecked = false;
        }
      },
      deep: true // 深度
    }
  }
}
</script>

<style lang="scss" scoped>
.per-page-selector {
  width: 90px;
}
</style>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
