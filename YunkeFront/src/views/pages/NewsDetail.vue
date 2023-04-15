<!--
  新闻详情页面
-->
<template>
    <div>
        <div class="mail-sidebar-content px-0 sm:py-6 pt-2 h-full">
            <div class="flex flex-wrap items-center email-header justify-between md:px-8 px-6 sm:pb-2">
                <div class="flex mb-4">
                    <div class="flex items-center" @click="backPrePage">
                        <feather-icon icon="ArrowLeftIcon" class="cursor-pointer mr-4" svg-classes="w-6 h-6"></feather-icon>
                        <h3>返回</h3>
                    </div>
                </div>
            </div>
            <VuePerfectScrollbar class="scroll-area md:px-8 pt-4 px-6">
              <!-- /LABEL ROW -->
              <br>
              <div v-if="true">
                  <!-- LATEST MESSAGE -->
                  <div class="vx-row">
                      <div class="vx-col w-full">
                          <vx-card class="px-4" v-if="currentNews != null" >

                              <!-- MAIL META ROW -->
                              <div class="vx-row w-full border-b border-l-0 border-r-0 border-t-0 d-theme-border-grey-light border-solid flex justify-between flex items-center">
                                  <div class="vx-col sm:w-4/5 w-full flex flex-wrap items-center mb-2">
                                      <div class="flex flex-col my-2">
                                          <h4 class="mb-1" style="color: rebeccapurple">{{currentNews.newsTitle}}</h4>
                                          <div class="flex items-center">
                                              <span style="font-size: 1rem;font-weight: bold">{{currentNews.username}}</span>
                                          </div>
                                      </div>
                                  </div>
                                  <div class="vx-col sm:w-1/5 w-full flex sm:flex-col items-center sm:justify-end mb-2">
                                      时间:<span class="flex self-end sm:mt-2 mt-0 whitespace-no-wrap">{{currentNews.createTime.replace("T", " ")}}</span>
                                  </div>
                              </div>

                              <!-- 新闻内容 -->
                              <div class="vx-row">
                                  <div class="vx-col w-full">
                                      <div class="mail__content break-words mt-8 mb-4" v-html="currentNews.newsContent"></div>
                                  </div>
                              </div>
                          </vx-card>
                      </div>
                  </div>
              </div>
            </VuePerfectScrollbar>
        </div>
    </div>
</template>

<script>
import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import {getNewsById} from "../../network";

export default{
    mounted() {
      this.currentNewsId = this.$store.state.newsDetail.id;
      getNewsById(this.currentNewsId).then(res => {
        if (res.data.code == 200) {
          this.currentNews = res.data.data;
        }
      }).catch(err => {
        console.log("err = ", err)
      })
    },
    data() {
        return {
            showThread: false,
            settings: {
                maxScrollbarLength: 60,
                wheelSpeed: 0.50,
            },
            currentNewsId: null,
            currentNews: null
        }
    },
    watch: {
        isSidebarActive(value) {
            if(!value) {
                this.$emit('closeSidebar');
                setTimeout(() => {
                    this.showThread = false;
                }, 500)
            }
        },
    },
    methods: {
        toggleIsStarred() {

        },
        backPrePage() {
          this.$router.back(-1);
        }
    },
    components: {
        VuePerfectScrollbar
    },
}
</script>
