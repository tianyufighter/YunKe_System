<!--
  新闻页面
-->
<template>
    <div id="news-page" class="d-theme-border-grey-light rounded relative overflow-hidden">
        <div class="search-page__search-bar flex items-center">
            <vs-input placeholder="搜索内容..." v-model="searchQuery" class="w-full input-rounded-full no-icon-border" icon="icon-search" icon-pack="feather" />
        </div>
        <div class="search-page__serch-menu flex flex-wrap items-center md:justify-between mt-8">
            <div class="flex flex-wrap">
                <span class="search-tab-filter" v-for="item in allTags" :key="item.id">{{item.tagName}}</span>
            </div>
        </div>
        <div class="search-meta flex flex-wrap justify-between mt-6">
            <span class="mb-4">实时热点新闻</span>
            <div>
                <vs-dropdown vs-trigger-click class="cursor-pointer">
                    <span class="flex items-center">
                        <span>发布时间</span>
                        <feather-icon icon="ChevronDownIcon" style="width:1rem; height:1rem" class="cursor-pointer"></feather-icon>
                    </span>
                    <vs-dropdown-menu class="w-48">
                        <vs-dropdown-item>任意时间</vs-dropdown-item>
                        <vs-dropdown-item>过去一小时</vs-dropdown-item>
                        <vs-dropdown-item>过去一天</vs-dropdown-item>
                        <vs-dropdown-item>过去一周</vs-dropdown-item>
                        <vs-dropdown-item>过去一个月</vs-dropdown-item>
                        <vs-dropdown-item>过去一年</vs-dropdown-item>
                    </vs-dropdown-menu>
                </vs-dropdown>
            </div>
        </div>

        <!-- SEARCH RESULTS -->
        <div class="vx-row mt-4 md:flex-row flex-col-reverse">
            <div class="vx-col md:w-3/5 lg:w-2/3 w-full">
                <vx-card class="search-page__search-results lg:p-2">
                    <div class="vx-row search-Page__search-result" v-for="(result, index) in news" :key="index" :class="{ 'mt-8': index }" @click.stop="openNews(result)">
                        <!--新闻图片-->
                        <div class="vx-col mb-2" :class="result.newsCover? 'lg:w-1/5 md:w-1/4 w-full' : 'w-full'" v-if="result.newsCover">
                            <img :src="result.newsCover" alt="result-img" class="responsive" v-if="result.newsCover">
                        </div>
                        <div class="vx-col" :class="result.newsCover ? 'lg:w-4/5 md:w-3/4' : 'w-full'">
                            <!--新闻标题-->
                            <a href="javascript:;" class="inline-block text-2xl"  rel="nofollow">{{ result.newsTitle }}</a><br>
                            <!--新闻描述-->
                            <span>{{ result.newsContent | truncate(225) | tailing('...') }}</span>
                        </div>
                    </div>
                </vx-card>

                <vs-pagination :total="totalPage" v-model="pageNum" class="mt-base"></vs-pagination>
            </div>

            <!-- 公告界面 -->
            <div class="vx-col md:w-2/5 lg:w-1/3 w-full mb-base">
              <vx-card
                  title="通告"
                  title-color="#fff"
                  content-color="#fff"
                  card-background="linear-gradient(120deg, #7f7fd5, #86a8e7, #91eae4)"
                  remove-card-action v-for="item in notice"
                  class="mb-base">
                 <strong>{{item.noticeContent}}</strong>
              </vx-card>
            </div>
        </div>
    </div>
</template>

<script>
import { videoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'
import newsDetail from './NewsDetail.vue'
import VuePerfectScrollbar from "vue-perfect-scrollbar";
import {getAllNewsTag, getAllNotice, getNewsByNum} from "../../network";

export default{
    data() {
        return {
          pageNum: 1,
          pageSize: 2,
          noticePageNum: 1, // 通告的当前请求页
          noticePageSize: 2, // 每次请求通告的数量
          openNewsId: 1,
          totalPage: 0,
          isSidebarActive: false,
          searchQuery: '',
          news: [],
          allTags: [],
          notice: [] // 通告
        }
    },
    computed: {
        playerOptions() {
            return (media) => {
                return {
                    height: '360',
                    fluid: true,
                    // rmeove this comment if you want to autoplay
                    // autoplay: true,
                    muted: true,
                    language: 'en',
                    playbackRates: [0.7, 1.0, 1.5, 2.0],
                    sources: media.sources,
                    poster: media.poster,
                }
            }
        }
    },
    watch: {
      pageNum(newVal) {
        // 分页获取信息新闻消息
        getNewsByNum({
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }).then(res => {
          console.log(res)
          if (res.data.code == 200) {
            this.news = res.data.data.recordList;
            this.totalPage = res.data.data.count;
          }
        }).catch(err => {
          console.error(err)
        })
      }
    },
    methods: {
      openNews(newsDetail) {
        this.$store.commit("updateNewsDetail", newsDetail)
        // 路由
        this.$router.push('/apps/news/newsDetail')
      },
      closeNewsDetail() {
        this.isSidebarActive = false;
      },

    },
    components: {
        videoPlayer,
        newsDetail
    },
    mounted() {
      // 获取所有的新闻标签
      getAllNewsTag().then(res => {
        if (res.data.code == 200) {
          this.allTags = res.data.data;
          // 获取所有新闻消息
          getNewsByNum({
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }).then(res => {
            console.log(res)
            if (res.data.code == 200) {
              this.news = res.data.data.recordList;
              this.totalPage = res.data.data.count;
            }
          }).catch(err => {
            console.error(err)
          })
        }
      }).catch(err => {
        console.error(err)
      })
      getAllNotice({
        pageNum: this.noticePageNum,
        pageSize: this.noticePageSize
      }).then(res => {
        if (res.data.code == 200) {
          this.notice = res.data.data;
        }
      }).catch(err => {
        console.error(err)
      })
    }
}
</script>

<style lang="scss">
@import "@/assets/scss/vuesax/pages/search.scss";
</style>
