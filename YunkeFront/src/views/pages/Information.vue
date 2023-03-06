<!-- 信息平台界面 -->

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
            <p class="font-semibold md:block hidden">云客信息平台</p>

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
                    @click="currentItemView='item-grid-view'"
                    class="p-2 shadow-drop rounded-lg d-theme-dark-bg cursor-pointer"
                    :svgClasses="{'text-primary stroke-current': currentItemView == 'item-grid-view'}" />
                <feather-icon
                    icon="ListIcon"
                    @click="currentItemView='item-list-view'"
                    class="p-2 ml-4 shadow-drop rounded-lg d-theme-dark-bg cursor-pointer hidden sm:inline-flex"
                    :svgClasses="{'text-primary stroke-current': currentItemView == 'item-list-view'}" />
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

            <vs-divider />

            <!-- CATEGORIES -->
            <h6 class="font-bold mb-4">信息类型</h6>
            <info-type @infoTypeEvent="infoFilter"></info-type>

<!--            <vs-divider />-->

<!--            <vs-button @click.prevent="true" :disabled="false">清除所有过滤</vs-button>-->
          </div>
        </vs-sidebar>

        <!-- RIGHT COL -->
        <div :class="{'sidebar-spacer-with-margin': clickNotClose}">
          <!-- 搜索框 -->
          <div class="relative mb-8">
            <!-- SEARCH INPUT -->
            <vs-input class="w-full vs-input-shadow-drop vs-input-no-border d-theme-input-dark-bg" :placeholder="searchPlaceholder" v-model="currentTitle"  size="large" />
            <!-- SEARCH ICON -->
            <div class="absolute top-0 right-0 py-4 px-6" style="cursor: pointer" @click="clickSearch">
              <feather-icon icon="SearchIcon" svgClasses="h-6 w-6" />
            </div>
          </div>
          <!-- SEARCH RESULT -->
            <div>
              <!-- GRID VIEW -->
              <template v-if="currentItemView == 'item-grid-view'">
                <div class="items-grid-view vx-row match-height">
                  <div class="vx-col lg:w-1/3 sm:w-1/2 w-full" v-for="item in info" :key="item.objectID">
                    <item-grid-view :item="item"></item-grid-view>
                  </div>
                </div>
              </template>

              <!-- LIST VIEW -->
              <template v-else>
                <div class="items-list-view" v-for="item in info" :key="item.objectID">
                  <item-list-view :item="item"></item-list-view>
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
import algoliasearch from 'algoliasearch/lite';
import FilterRadio from './FilterRadio.vue'
import InfoType from "./InfoType";
import {getInfoByNum} from "../../network";

export default {
  components: {
    ItemGridView: () => import("./ItemGridView.vue"),
    ItemListView: () => import("./ItemListView.vue"),
    FilterRadio,
    InfoType
  },
  data() {
    return {
      searchPlaceholder: '搜索...',
      currentTitle: '',
      totalPage: 0, // 总页数
      currentPageNum: 1, // 当前所在的页面
      pageSize: 6, // 页面上展示信息的个数
      infoType: 'allType',
      timeScope: 'noban',
      infoStartTime: null,
      infoEndTime: null,
      // Filter Sidebar
      isFilterSidebarActive: true,
      clickNotClose: true,
      windowWidth: window.innerWidth,
      currentItemView: 'item-grid-view',
      sortTime: '最新发布',
      sortType: [
        { text: '最新发布', value: '最新发布' },
        { text: '最早发布', value: '最早发布' },
      ],
      info: [
        { infoCover: require(`@/assets/images/pages/404.png`), infoTitle: '皮鞋', infoContent: '这是在意大利进口的', username: 'Titos', email: '634522023@qq.com', type: 1, createTime: '2022-03-22'},
        { infoCover: require(`@/assets/images/pages/404.png`), infoTitle: '招小牛马', infoContent: '这是在意大利进口的', username: 'Titos', email: '634522023@qq.com', type: 2, createTime: '2022-03-22'},
        { infoCover: require(`@/assets/images/pages/404.png`), infoTitle: '丢失一卡通', infoContent: '这是在意大利进口的', username: 'Titos', email: '634522023@qq.com', type: 3, createTime: '2022-03-22'},
        { infoCover: require(`@/assets/images/pages/404.png`), infoTitle: '零食', infoContent: '这是在意大利进口的', username: 'Titos', email: '634522023@qq.com', type: 1, createTime: '2022-03-22'}
      ]
    };
  },
  computed: {
    toValue() {
      return (value, range) =>
          [
            value.min !== null ? value.min : range.min,
            value.max !== null ? value.max : range.max,
          ];
    },
    infoTypeNum() {
      if (this.infoType == 'allType') {
        return null;
      } else if (this.infoType == 'goods') {
        return 1;
      } else if (this.infoType == 'job') {
        return 2;
      } else if (this.infoType == 'lost') {
        return 3;
      }
    }
  },
  watch: {
    currentTitle(newVal) {
      if (newVal != "") {
        this.searchPlaceholder = '';
      } else {
        this.clickSearch();
        this.searchPlaceholder = '搜索...';
      }
    },
    currentPageNum(newVal) {
      getInfoByNum({
        startTime: this.infoStartTime,
        endTime: this.infoEndTime,
        type: this.infoTypeNum,
        pageNum: this.currentPageNum,
        pageSize: this.pageSize
      }).then(res => {
        this.info = []
        if (res.data.code == 200) {
          this.totalPage = res.data.data.count;
          for (let i = 0; i < res.data.data.recordList.length; i++) {
            this.info.push(res.data.data.recordList[i])
          }
        }
      }).catch(err => {
        console.log("err = ", err)
      })
    },
    sortTime: {
      handler(newVal) {
        this.currentPageNum = 1;
        getInfoByNum({
          startTime: this.infoStartTime,
          endTime: this.infoEndTime,
          type: this.infoTypeNum,
          pageNum: this.currentPageNum,
          pageSize: this.pageSize,
          isEarliest: this.sortTime == '最早发布' ? true : false
        }).then(res => {
          if (res.data.code == 200) {
            this.info = res.data.data.recordList;
            this.totalPage = res.data.data.count;
          }
          console.log("试题 = ", this.info)
        }).catch(err => {
          console.log("err = ", err)
        })
      },
      immediate: false
    }
  },
  methods: {
    clickSearch() {
      this.currentPageNum = 1;
      getInfoByNum({
        startTime: this.infoStartTime,
        endTime: this.infoEndTime,
        type: this.infoTypeNum,
        pageNum: this.currentPageNum,
        pageSize: this.pageSize,
        isEarliest: this.sortTime == '最早发布' ? true : false,
        matchTitle: this.currentTitle
      }).then(res => {
        if (res.data.code == 200) {
          this.totalPage = res.data.data.count;
          this.info = res.data.data.recordList;
        }
        console.log("试题 = ", this.info)
      }).catch(err => {
        console.log("err = ", err)
      })
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
    infoFilter(value) {
      this.currentPageNum = 1;
      this.infoType = value;
      getInfoByNum({
        startTime: this.infoStartTime,
        endTime: this.infoEndTime,
        type: this.infoTypeNum,
        pageNum: this.currentPageNum,
        pageSize: this.pageSize
      }).then(res => {
        this.info = [];
        for (let i = 0; i < res.data.data.recordList.length; i++) {
          this.info.push(res.data.data.recordList[i]);
        }
        this.totalPage = res.data.data.count;
      }).catch(err => {
        console.log("err = ", err)
      })
    },
    timeFilter(value) {
      this.currentPageNum = 1;
      this.timeScope = value;
      if (this.timeScope == 'noban') {
        this.infoStartTime = null;
        this.infoEndTime = null;
      } else if (this.timeScope == 'week') {
        this.infoStartTime = this.dateFormat(new Date(new Date().setDate(new Date().getDate() - 7))).substr(0, 10) + " 00:00:00";
        this.infoEndTime = this.dateFormat(new Date(new Date())).substr(0, 10) + " 00:00:00";
      } else if (this.timeScope == 'month') {
        this.infoStartTime = this.dateFormat(new Date(new Date().setDate(new Date().getDate() - 30))).substr(0, 10) + " 00:00:00";
        this.infoEndTime = this.dateFormat(new Date(new Date())).substr(0, 10) + " 00:00:00";
      }
      getInfoByNum({
        startTime: this.infoStartTime,
        endTime: this.infoEndTime,
        type: this.infoTypeNum,
        pageNum: this.currentPageNum,
        pageSize: this.pageSize
      }).then(res => {
        if (res.data.code == 200) {
          this.info = res.data.data.recordList;
          this.totalPage = res.data.data.count;
        }
      }).catch(err => {
        console.log(err)
      })
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
    }
  },
  mounted() {
    getInfoByNum({
      startTime: this.infoStartTime,
      endTime: this.infoEndTime,
      type: this.infoTypeNum,
      pageNum: this.currentPageNum,
      pageSize: this.pageSize
    }).then(res => {
      console.log(res)
      this.info = []
      if (res.data.code == 200) {
        this.totalPage = res.data.data.count;
        for (let i = 0; i < res.data.data.recordList.length; i++) {
          this.info.push(res.data.data.recordList[i]);
        }
      }
    }).catch(err => {
      console.log("err = ", err)
    })
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
