<!-- 博客信息平台界面 -->

<template>
  <div>
    <div index-name="instant_search" id="algolia-instant-search-demo">

      <!-- AIS CONFIG -->
<!--      <ais-configure :hits-per-page.camel="9" />-->

      <div class="algolia-header mb-4">
        <div class="flex md:items-end items-center justify-between flex-wrap">
          <!-- TOGGLE SIDEBAR BUTTON -->
          <feather-icon
              class="inline-flex lg:hidden cursor-pointer mr-4"
              icon="MenuIcon"
              @click.stop="toggleFilterSidebar" />

          <p class="lg:inline-flex hidden font-semibold algolia-filters-label">过滤</p>

          <div class="flex justify-between items-end flex-grow">
            <!--查询结果提示信息-->
            <p class="font-semibold md:block hidden">云客博客平台</p>

            <div class="flex flex-wrap">

              <!-- 排序 -->
              <div style="margin-right: 1rem">
                <vs-select v-model="sortTime">
                  <vs-select-item :key="item.value" :value="item.value" :text="item.text" v-for="item in sortType" />
                </vs-select>
              </div>
              <!-- ITEM VIEW - GRID/LIST -->
              <div>
                <feather-icon
                    icon="GridIcon"
                    @click="showSystemData"
                    class="p-2 shadow-drop rounded-lg d-theme-dark-bg cursor-pointer"
                    :svgClasses="{'text-primary stroke-current': currentItemView == 'systemData'}" />
                <feather-icon
                    icon="UserIcon"
                    @click="showPersonalData"
                    class="p-2 ml-4 shadow-drop rounded-lg d-theme-dark-bg cursor-pointer hidden sm:inline-flex"
                    :svgClasses="{'text-primary stroke-current': currentItemView == 'personalData'}" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div id="algolia-content-container" class="relative clearfix">
        <vs-sidebar
            class="items-no-padding vs-sidebar-rounded background-absolute"
            parent="#algolia-content-container"
            :click-not-close="clickNotClose"
            :hidden-background="clickNotClose"
            v-model="isFilterSidebarActive">

          <div class="p-6 filter-container">
            <!-- MULTI RANGE -->
            <h6 class="font-bold mb-3">不同范围</h6>
            <filter-radio @infoTimeEvent="timeFilter"></filter-radio>
            <!-- CATEGORIES -->
            <div v-if="currentItemView=='personalData'">
              <vs-divider />
              <vx-card>
                <h4>博客分类</h4>
                <ul class="faq-topics mt-4">
                  <li v-for="(item, index) in blogType" :key="index" class="p-2 font-medium flex items-center" @click="getBlogByType(item.name)">
                    <div class="h-3 w-3 rounded-full mr-2" :style="{backgroundColor: item.color}"></div><span class="cursor-pointer">{{ item.name }}</span>
                  </li>
                </ul>
              </vx-card>
            </div>
          </div>
        </vs-sidebar>

        <!-- RIGHT COL -->
        <div :class="{'sidebar-spacer-with-margin': clickNotClose}">
          <!-- 搜索框 -->
          <div class="relative mb-8">
            <!-- SEARCH INPUT -->
            <vs-input class="w-full vs-input-shadow-drop vs-input-no-border d-theme-input-dark-bg" :placeholder="searchPlaceholder" v-model="searchKeyword"  size="large" />
            <!-- SEARCH ICON -->
            <div class="absolute top-0 right-0 py-4 px-6" style="cursor: pointer" @click="clickSearch">
              <feather-icon icon="SearchIcon" svgClasses="h-6 w-6" />
            </div>
          </div>
          <!-- SEARCH RESULT -->
            <div>
              <!-- LIST VIEW -->
              <template>
                <div class="items-list-view" v-for="item in blogList" :key="item.id">
                  <blog-item-list-view :item="item"></blog-item-list-view>
                </div>
              </template>
            </div>

          <!-- 翻页 -->
          <vs-pagination :total="totalPage" v-model="currentPageNum"></vs-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FilterRadio from './FilterRadio.vue'
import {getAllArticleType, getArticleByCondition, getInfoByNum} from "../../network";
import {getBlogByCondition, getMyBlogByCondition} from "@network/blog";

export default {
  components: {
    BlogItemListView: () => import("./BlogItemListView.vue"),
    FilterRadio,
  },
  data() {
    return {
      searchPlaceholder: '搜索...',
      searchKeyword: '', // 搜索文章的关键字
      totalPage: 0, // 总页数
      currentPageNum: 1, // 当前所在的页面
      pageSize: 2, // 页面上展示信息的个数
      timeScope: 'noban',
      infoStartTime: null,
      infoEndTime: null,
      // Filter Sidebar
      isFilterSidebarActive: true,
      clickNotClose: true,
      windowWidth: window.innerWidth,
      currentItemView: 'systemData',
      sortTime: '最新发布',
      sortType: [
        { text: '最新发布', value: '最新发布' },
        { text: '最早发布', value: '最早发布' },
      ],
      blogType: null,
      blogList: null,
    };
  },
  watch: {
    currentItemView(newVal) {
      if (newVal == 'personalData') {
        getAllArticleType().then(res => {
          if (res.data.code == 200) {
            let color = `rgb(${this.getRandomInt(0,255)},${this.getRandomInt(0,255)},${this.getRandomInt(0,255)})`
            this.blogType = [{
              name: '所有',
              color: color
            }];
            for (let i = 0; i < res.data.data.length; i++) {
              let color = `rgb(${this.getRandomInt(0,255)},${this.getRandomInt(0,255)},${this.getRandomInt(0,255)})`;
              this.blogType.push({
                name: res.data.data[i],
                color: color
              })
            }
          }
        }).catch(err => {
          console.error(err);
        })
      }
    },
    currentPageNum(newVal) {
      this.fetchBlog({
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        isEarliest: this.sortTime == '最早发布' ? true : false,
        articleTitle: this.searchKeyword,
        articleContent: this.searchKeyword,
        status: 1,
        violation: 0,
        params: {
          beginTime: this.blogStartTime,
          endTime: this.blogEndTime
        }
      })
    },
    sortTime: {
      handler(newVal) {
        this.currentPageNum = 1;
        this.fetchBlog({
          pageNum: this.currentPageNum,
          pageSize: this.pageSize,
          isEarliest: this.sortTime == '最早发布' ? true : false,
          status: 1,
          violation: 0,
          params: {
            beginTime: this.blogStartTime,
            endTime: this.blogEndTime
          }
        })
      },
      immediate: false
    }
  },
  methods: {
    getBlogByType(blogTypeName) {
      if (blogTypeName == '所有') {
        blogTypeName = null;
      }
      this.currentPageNum = 1;
      this.fetchMyBlog({
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        violation: 0,
        category: blogTypeName
      })
    },
    getRandomInt(min, max) {
      return Math.floor(Math.random() * (max - min)) + min;
    },
    // 当用户查询所有的博客信息时
    showSystemData() {
      this.currentItemView = 'systemData';
      this.currentPageNum = 1;
      this.fetchBlog({
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        status: 1,
        violation: 0
      });
    },
    // 当用户查询个人的博客信息时
    showPersonalData() {
      this.currentItemView = 'personalData';
      this.currentPageNum = 1;
      this.fetchMyBlog({
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        violation: 0,
      });
    },
    clickSearch() {
      this.currentPageNum = 1;
      let data = {
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        isEarliest: this.sortTime == '最早发布' ? true : false,
        articleContent: this.searchKeyword,
        violation: 0,
        params: {
          beginTime: this.blogStartTime,
          endTime: this.blogEndTime
        }
      };
      if (this.currentItemView == 'systemData') {
        data.status = 1;
        this.fetchBlog(data);
      } else {
        this.fetchMyBlog(data);
      }
    },
    handleWindowResize(event) {
      this.windowWidth = event.currentTarget.innerWidth;
      this.setSidebarWidth();
    },
    setSidebarWidth() {
      if(this.windowWidth < 992) {
        this.isFilterSidebarActive = this.clickNotClose = false;
      }else {
        this.isFilterSidebarActive = this.clickNotClose = true;
      }
    },
    // GRID VIEW - ACTIONS
    toggleFilterSidebar() {
      if(this.clickNotClose) return
      this.isFilterSidebarActive = !this.isFilterSidebarActive;
    },
    timeFilter(value) {
      this.currentPageNum = 1;
      this.timeScope = value;
      if (this.timeScope == 'noban') {
        this.blogStartTime = null;
        this.blogEndTime = null;
      } else if (this.timeScope == 'week') {
        this.blogStartTime = this.dateFormat(new Date(new Date().setDate(new Date().getDate() - 7))).substr(0, 10) + " 00:00:00";
        this.blogEndTime = this.dateFormat(new Date(new Date())).substr(0, 10) + " 00:00:00";
      } else if (this.timeScope == 'month') {
        this.blogStartTime = this.dateFormat(new Date(new Date().setDate(new Date().getDate() - 30))).substr(0, 10) + " 00:00:00";
        this.blogEndTime = this.dateFormat(new Date(new Date())).substr(0, 10) + " 00:00:00";
      }
      let data = {
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        status: 1,
        violation: 0,
        params: {
          beginTime: this.blogStartTime,
          endTime: this.blogEndTime
        }
      };
      if (this.currentItemView == 'systemData') {
        data.status = 1;
        this.fetchBlog(data)
      } else {
        this.fetchMyBlog(data)
      }
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
    // 获取博客数据
    fetchBlog(data) {
      getBlogByCondition(data).then(res => {
        if (res.data.code == 200) {
          this.blogList = res.data.data.list;
          this.totalPage = res.data.data.pages;
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    },
    // 获取个人博客数据
    fetchMyBlog(data) {
      getMyBlogByCondition(data).then(res => {
        if (res.data.code == 200) {
          this.blogList = res.data.data.list;
          this.totalPage = res.data.data.total;
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    }
  },
  mounted() {
    this.fetchBlog({
      pageNum: this.currentPageNum,
      pageSize: this.pageSize,
      violation: 0
    });
  },
  created() {
    this.$nextTick(() => {
      window.addEventListener('resize', this.handleWindowResize);
    })
    this.setSidebarWidth();
  },
  beforeDestroy: function() {
    window.removeEventListener('resize', this.handleWindowResize)
  },
};
</script>

<style lang="scss">
#algolia-instant-search-demo {
  .algolia-header {
    .algolia-filters-label {
      width: calc(260px + 2.4rem);
    }
  }

  #algolia-content-container {

    .vs-sidebar {
      position: relative;
      float: left;
    }
  }

  .algolia-search-input-right-aligned-icon {
    padding: 1rem 1.5rem;
  }

  .algolia-price-slider {
    min-width: unset;
  }

  .item-list-view {
    .algolia-result-img {

    }
  }

  .item-view-primary-action-btn {
    color: #2c2c2c !important;
    background-color: #f6f6f6;
    min-width: 50%;
  }

  .item-view-secondary-action-btn {
    min-width: 50%;
  }
}

.theme-dark {
  #algolia-instant-search-demo {
    #algolia-content-container {
      .vs-sidebar {
        background-color: #10163a;
      }
    }
  }
}

@media (min-width: 992px) {
  .vs-sidebar-rounded {
    .vs-sidebar { border-radius: .5rem; }
    .vs-sidebar--items {border-radius: .5rem; }
  }
}

@media (max-width: 992px) {
  #algolia-content-container {
    .vs-sidebar {
      position: absolute !important;
      float: none !important;
    }
  }
}
</style>
