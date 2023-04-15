<!--用户管理界面-->
<template>
  <div>
    <!-- 筛选用户 -->
    <blog-setting-filters
      :status-options="statusFilterOptions"
      :violation-options="violationOptions"
      @clickReset="refetchBlogList"
      @clickSearch="getBlogByCondition"
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
                @click="deleteBlog"
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
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="filterBlogList"
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
        <!--Column: 标题-->
        <template #cell(articleTitle)="data">
          <b-badge
              pill
              :variant="`light-primary`"
              class="text-capitalize"
          >
            <span v-if="data.item.articleTitle.length > 10">{{data.item.articleTitle | truncate(10) | tailing('...')}}</span>
            <span v-else>{{data.item.articleTitle}}</span>
          </b-badge>
        </template>
        <!--Column: 内容-->
        <template #cell(articleText)="data">
          <b-badge
              pill
              :variant="`light-dark`"
              class="text-capitalize"
          >
            <span v-if="data.item.articleText.length > 20">{{data.item.articleText | truncate(20) | tailing('...')}}</span>
            <span v-else>{{data.item.articleText}}</span>
          </b-badge>
        </template>
        <!--Column: 目录-->
        <template #cell(category)="data">
          <b-badge
              pill
              :variant="`light-info`"
              class="text-capitalize"
          >
            {{data.item.category}}
          </b-badge>
        </template>
        <!--Column: 发布时间-->
        <template #cell(createTime)="data">
          <span>{{data.item.createTime.replace("T"," ")}}</span>
        </template>
        <!--Column: 状态-->
        <template #cell(status)="data">
          <b-badge
              pill
              :variant="`light-${resolveStatusVariant(data.item.status)}`"
              class="text-capitalize"
          >
            {{ data.item.status == 1 ? '公开' : '私密' }}
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

            <b-dropdown-item @click="deleteBlog(data.item)">
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
              :total-rows="totalBlog"
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
        title="博客信息"
        ok-title="更改"
        cancel-title="取消"
        cancel-variant="outline-secondary"
        @cancel=""
        @hidden=""
        @ok="submitBlog"
    >
      <b-form>
        <b-form-group
            label="博客标题"
            label-for="configName"
        >
          <b-form-input
              id="configName"
              v-model="articleTitle"
              trim
          />
        </b-form-group>

        <b-form-group
            label="博客目录"
            label-for="configKey"
        >
          <b-form-input
              id="configKey"
              v-model="category"
              trim
          />
        </b-form-group>

        <b-form-group
            label="状态"
            label-for="configType"
        >
          <b-form-radio-group
              id="isViolation"
              v-model="status"
              :options="statusOptions"
              class="demo-inline-spacing mb-1"
              value-field="value"
              text-field="text"
              disabled-field="disabled"
          />
        </b-form-group>

        <b-form-group
            label="审核状态"
            label-for="configType"
        >
          <v-select
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              v-model="violation"
              :options="violationOptions"
              :reduce="val => val.value"
              :clearable="false"
              input-id="configType"
          />
        </b-form-group>

        <b-form-group
            label="博客内容"
            label-for="remark"
        >
          <mavon-editor
              ref="md"
              placeholder="请输入文章内容..."
              :boxShadow="false"
              style="z-index: 1; border: 1px solid #d9d9d9; height: 70vh;"
              v-model="articleText"
              :external-link="externalLink"
              :toolbars="toolbars"
              code-style="atom-one-dark"
              :ishljs="true"
          >
          </mavon-editor>
        </b-form-group>

      </b-form>
    </b-modal>
  </div>
</template>

<script>
import {
  BCard, BRow, BCol, BFormInput, BButton, BTable, BMedia, BAvatar, BLink,BFormRadioGroup,
  BBadge, BDropdown, BDropdownItem, BPagination, VBModal, BForm, BFormGroup,BFormTextarea,BFormCheckbox,
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import { avatarText, title } from '@core/utils/filter'
import BlogSettingFilters from '@/views/apps/data-manage/blog-manage/BlogSettingFilters.vue'
import BlogSettingAddNew from '@/views/apps/data-manage/blog-manage/BlogSettingAddNew.vue'
import Ripple from 'vue-ripple-directive'
import {getBlog, updateBlog, deleteBlogBatch} from '@/network/blogmanage'

export default {
  components: {
    BlogSettingFilters,
    BlogSettingAddNew,
    BFormRadioGroup,
    BFormTextarea,
    BFormGroup,
    BFormCheckbox,
    BForm,
    BCard,
    BRow,
    BCol,
    BFormInput,
    BButton,
    BTable,
    BMedia,
    BAvatar,
    BLink,
    BBadge,
    BDropdown,
    BDropdownItem,
    BPagination,

    vSelect,
  },
  directives: {
    'b-modal': VBModal,
    Ripple,
  },
  data() {
    return {
      selectedSystemInner: 'yes',
      statusOptions: [
        { text: '公开', value: 1, disabled: false },
        { text: '私密', value: 2, disabled: false },
      ],
      violationOptions: [
        { label: '正常', value: 0 },
        { label: '违规', value: 1 },
        { label: '待审核', value: 2 },
      ],
      statusFilterOptions: [
        { label: '公开', value: 1},
        { label: '私密', value: 2},
      ],
      tableColumns: [
        { key: '#', label: '#'},
        { key: 'articleTitle', label: '博客标题', sortable: true },
        { key: 'articleText', label: '博客内容', sortable: true },
        { key: 'category', label: '目录', sortable: true, },
        { key: 'createTime', label: '发布时间' },
        { key: 'status', label: '状态'},
        { key: 'violation', label: '是否合规' },
        { key: 'userId', label: '用户id' },
        { key: 'actions', label: '操作' },
      ],
      perPage: 5, // 每页显示的条数
      searchQuery: '', // 搜索所对应的关键字
      totalBlog: 0, // 总的帖子数
      currentPage: 1,
      perPageOptions: [10, 25, 50, 100],
      sortBy: 'id',
      isSortDirDesc: true,
      blogList: null,
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
      articleTitle: null,
      category: null,
      status: null,
      violation: null,
      articleText: null,
      articleContent: null
    }
  },
  computed: {
    filterBlogList() {
      return this.blogList?.filter(blog => {
        return (blog.articleTitle?.includes(this.searchQuery) ||
                blog.articleText?.includes(this.searchQuery)
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
    deleteBlog(data) {
      if (data != null) {
        this.idArr.push(data.id);
      }
      deleteBlogBatch(this.idArr).then(res => {
        if (res.data.code == 200 ) {
          this.refetchBlogList();
          this.$vs.notify({
            title:'消息提示',
            text:'博客信息删除成功',
            color:'success',
            position:'top-center'})
        } else {
          this.$vs.notify({
            title:'错误提示',
            text:'博客信息删除失败',
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
    getBlogByCondition(data) {
      this.currentPage = 1;
      this.perPage = 5;
      getBlog({
        pageNum: this.currentPage,
        pageSize: this.perPage,
        articleTitle: data.articleTitle,
        articleText: data.articleText,
        status: data.status,
        violation: data.violation
      }).then(res => {
        if (res.data.code == 200) {
          this.blogList = res.data.data.list;
          this.totalBlog = res.data.data.total;
          this.blogList.forEach(post => {
            this.$set(this.isCheckedObj, 'index' + post.id, false);
          })
        } else {
          Promise.reject(res);
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
    // 重新获取所有的的博客信息
    refetchBlogList() {
      getBlog({
        pageNum: this.currentPage,
        pageSize: this.perPage
      }).then(res => {
        if (res.data.code == 200) {
          this.blogList = res.data.data.list;
          this.totalBlog = res.data.data.total;
          this.blogList.forEach(post => {
            this.$set(this.isCheckedObj, 'index' + post.id, false);
          })
        } else {
          Promise.reject(res);
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息查询失败',
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
      this.articleTitle = data.articleTitle;
      this.category = data.category;
      this.status = data.status;
      this.violation = data.violation;
      this.articleContent = data.articleContent;
      this.articleText = data.articleText;
      this.$refs['modal-select2'].show();
    },
    resetForm() {
      this.id = null;
      this.articleTitle = null;
      this.category = null;
      this.status = null;
      this.violation = null;
      this.articleContent = null;
      this.articleText = null;
    },
    submitBlog() {
      updateBlog({
        id: this.id,
        articleTitle: this.articleTitle,
        category: this.category,
        status: this.status,
        violation: this.violation,
        articleContent: this.$refs.md.d_render,
        articleText: this.$refs.md.d_value
      }).then(res => {
        if (res.data.code == 200) {
          this.refetchBlogList();
          this.resetForm();
          this.$vs.notify({
            title:'信息提示',
            text:'博客信息更新成功',
            color:'success',
            position:'top-center'})
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息更新失败',
          color:'danger',
          position:'top-center'})
      })
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
    this.refetchBlogList();
  },
  watch: {
    currentPage() {
      this.refetchBlogList();
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
