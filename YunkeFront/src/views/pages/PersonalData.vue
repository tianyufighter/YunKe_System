<!--
  菜单个人数据对应的页面
-->
<template>
  <div id="dashboard-analytics">
    <!--轮播图-->
    <div class="vx-row">
      <div class="vx-col w-full mb-base">
        <home-carousel></home-carousel>
      </div>
    </div>
    <div class="vx-row">
      <!-- CARD 1: CONGRATS -->
      <div class="vx-col w-full lg:w-1/2 mb-base">
        <vx-card slot="no-body" class="text-center bg-primary-gradient greet-user">
                    <img src="../../assets/images/elements/decore-left.png" class="decore-left" alt="Decore Left" width="200" >
                    <img src="../../assets/images/elements/decore-right.png" class="decore-right" alt="Decore Right" width="175">
          <feather-icon icon="AwardIcon" class="p-6 mb-8 bg-primary inline-flex rounded-full text-white shadow" svgClasses="h-8 w-8"></feather-icon>
          <h1 class="mb-6 text-white">Congratulations {{username}}</h1>
          <p class="xl:w-3/4 lg:w-4/5 md:w-2/3 w-4/5 mx-auto text-white">欢迎来到<strong>云客在线服务系统</strong></p>
        </vx-card>
      </div>

      <!-- CARD 2: 帖子获赞数量可视化展示 -->
      <div class="vx-col w-full sm:w-1/2 md:w-1/2 lg:w-1/4 xl:w-1/4 mb-base">
        <statistics-card-line icon="HeartIcon" v-if="subscribersGained.series[0].data.length != 0" :statistic="totalPostLike" statisticTitle="帖子获赞量" :chartData="subscribersGained" type='area'></statistics-card-line>
      </div>

      <!-- CARD 3: 帖子发布数量可视化展示 -->
      <div class="vx-col w-full sm:w-1/2 md:w-1/2 lg:w-1/4 xl:w-1/4 mb-base">
        <statistics-card-line icon="TwitterIcon" v-if="ordersRecevied.series[0].data.length != 0" :statistic="totalPublished" statisticTitle="帖子发布量" :chartData="ordersRecevied" color='warning' type='area'></statistics-card-line>
      </div>
    </div>

    <div class="vx-row">
      <!-- CARD 4: 系统人数情况 -->
      <div class="vx-col w-full md:w-1/3 mb-base">
        <vx-card title="系统在线人数">
            <template slot="actions">
                <feather-icon icon="HelpCircleIcon" svgClasses="w-6 h-6 text-grey"></feather-icon>
            </template>

            <!-- CHART -->
            <template slot="no-body">
                <div class="mt-10">
                    <vue-apex-charts v-if="totalUserCount != 0" type=radialBar height=240 :options="analyticsData.goalOverviewRadialBar.chartOptions" :series="[(this.onlineUserCount/this.totalUserCount * 100).toFixed(2)]" />
                </div>
            </template>

            <!-- DATA -->
            <div class="flex justify-between text-center" slot="no-body-bottom">
                <div class="w-1/2 border border-solid d-theme-border-grey-light border-r-0 border-b-0 border-l-0">
                    <p class="mt-4">活跃用户</p>
                    <p class="mb-4 text-3xl font-semibold">{{onlineUserCount}}</p>
                </div>
                <div class="w-1/2 border border-solid d-theme-border-grey-light border-r-0 border-b-0">
                    <p class="mt-4">系统总人数</p>
                    <p class="mb-4 text-3xl font-semibold">{{totalUserCount}}</p>
                </div>
            </div>
        </vx-card>
      </div>

      <!-- CARD 5: SUPPORT TRACKER -->
      <div class="vx-col w-full md:w-2/3 mb-base">
        <vx-card title="博客数据统计">
          <template slot="actions">
              <feather-icon icon="SettingsIcon" svgClasses="w-6 h-6 text-grey"></feather-icon>
          </template>
          <div slot="no-body" class="p-6 pb-0">
              <div class="flex">
                  <div class="mr-6">
                      <p class="mb-1 font-semibold">今日发布数量</p>
                      <p class="text-3xl text-success">{{todayReleaseBlogNum}}<sup class="text-base mr-1">篇</sup></p>
                  </div>
                  <div>
                      <p class="mb-1 font-semibold">总的发布数量</p>
                      <p class="text-3xl">{{totalReleaseBlogNum}}<sup class="text-base mr-1">篇</sup></p>
                  </div>
              </div>
              <vue-apex-charts v-if="revenueComparisonLine.series[0].data.length != 0" type=line height=266 :options="revenueComparisonLine.chartOptions" :series="revenueComparisonLine.series" />
          </div>
        </vx-card>
      </div>
    </div>
  </div>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
import StatisticsCardLine from '@/components/statistics-cards/StatisticsCardLine.vue'
import analyticsData from '../../analyticsData.js'
import ChangeTimeDurationDropdown from '@/components/ChangeTimeDurationDropdown.vue'
import HomeCarousel from './HomeCarousel.vue'
import {getCardStatus, getOnlineNum, getUserBlogNum} from "../../network";

export default {
    name: 'PersonalData',
    data() {
        return {
            onlineUserCount: 0, // 在线人数
            totalUserCount: 0, // 系统人数
            todayReleaseBlogNum: 0, // 今日发布博客的数量
            totalReleaseBlogNum: 0, // 总的发布博客的数量
            totalPostLike: 0, // 总帖子获赞量
            totalPublished: 0, // 总帖子发布量
            analyticsData: analyticsData,
            isImp: false,
            navbarSearchAndPinList: this.$store.state.navbarSearchAndPinList,
            show: false,
            username: '',
            items: [1, 2, 3, 4, 5, 6, 7, 8, 9],
            nextNum: 10,
            'tableList': [
                'vs-th: Component',
                'vs-tr: Component',
                'vs-td: Component',
                'thread: Slot',
                'tbody: Slot',
                'header: Slot'
            ],
            // 用户博客可视化需要的数据
            revenueComparisonLine: {
              series: [
                {
                  name: "本月",
                  data: []
                },
                {
                  name: "上月",
                  data: []
                }
              ],
              chartOptions: {
                chart: {
                  toolbar: { show: false },
                },
                stroke: {
                  curve: 'smooth',
                  dashArray: [0, 8],
                  width: [4, 2],
                },
                grid: {
                  borderColor: '#e7e7e7',
                },
                legend: {
                  show: false,
                },
                colors: ['#F97794', '#b8c2cc'],
                fill: {
                  type: 'gradient',
                  gradient: {
                    shade: 'dark',
                    inverseColors: false,
                    gradientToColors: ['#7367F0', '#b8c2cc'],
                    shadeIntensity: 1,
                    type: 'horizontal',
                    opacityFrom: 1,
                    opacityTo: 1,
                    stops: [0, 100, 100, 100]
                  },
                },
                markers: {
                  size: 0,
                  hover: {
                    size: 5
                  }
                },
                xaxis: {
                  labels: {
                    style: {
                      cssClass: 'text-grey fill-current',
                    }
                  },
                  axisTicks: {
                    show: false,
                  },
                  categories: [],
                  axisBorder: {
                    show: false,
                  },
                  title: {
                    text: '日期'
                  }
                },
                yaxis: {
                  tickAmount: 5,
                  labels: {
                    style: {
                      cssClass: 'text-grey fill-current',
                    },
                  },
                  title: {
                    text: '博客发布量(篇)'
                  }
                },
                tooltip: {
                  x: { show: false },
                  y: {
                    formatter: function (val) {
                      return val + "篇";
                    }
                  }
                }
            }
          },
            subscribersGained: {
            series: [{
              name: '获赞量',
              data: []
            }],
            chartOptions: {
              grid: {
                show: false,
                padding: {
                  left: 0,
                  right: 0
                }
              },
              chart: {
                toolbar: {
                  show: false,
                },
                sparkline: {
                  enabled: true
                }
              },
              dataLabels: {
                enabled: false
              },
              stroke: {
                curve: 'smooth',
                width: 2.5
              },
              fill: {
                type: 'gradient',
                gradient: {
                  shadeIntensity: 0.9,
                  opacityFrom: 0.7,
                  opacityTo: 0.5,
                  stops: [0, 80, 100]
                }
              },
              xaxis: {
                type: 'numeric',
                lines: {
                  show: false,
                },
                axisBorder: {
                  show: false,
                },
                labels: { show: false }
              },
              yaxis: [{
                y: 0,
                offsetX: 0,
                offsetY: 0,
                padding: { left: 0, right: 0 },
              }],
              tooltip: {
                x: { show: false }
              },
            },
          },
            ordersRecevied: {
            series: [{
              name: '发布量',
              data: []
            }],
            chartOptions: {
              grid: {
                show: false,
                padding: {
                  left: 0,
                  right: 0
                }
              },
              chart: {
                toolbar: {
                  show: false,
                },
                sparkline: {
                  enabled: true
                }
              },
              dataLabels: {
                enabled: false
              },
              stroke: {
                curve: 'smooth',
                width: 2.5
              },
              fill: {
                type: 'gradient',
                gradient: {
                  shadeIntensity: 0.9,
                  opacityFrom: 0.7,
                  opacityTo: 0.5,
                  stops: [0, 80, 100]
                }
              },
              xaxis: {
                type: 'numeric',
                lines: {
                  show: false,
                },
                axisBorder: {
                  show: false,
                },
                labels: { show: false }
              },
              yaxis: [{
                y: 0,
                offsetX: 0,
                offsetY: 0,
                padding: { left: 0, right: 0 },
              }],
              tooltip: {
                x: { show: false }
              },
            },
          },
        }
    },
    components: {
        VueApexCharts,
        StatisticsCardLine,
        ChangeTimeDurationDropdown,
        HomeCarousel,
    },
    mounted() {
      this.username = localStorage.getItem("username");
      // 获取在线人数等情况
      getOnlineNum().then(res => {
        if (res.data.code == 200) {
          this.onlineUserCount = res.data.data.onlineUserCount;
          this.totalUserCount = res.data.data.totalUserCount;
        }
      }).catch(err => {
        console.error(err)
      })
    },
  created() {
    // 获取用户博客信息
    getUserBlogNum().then(res => {
      if (res.data.code == 200) {
        this.todayReleaseBlogNum = res.data.data.todayNum;
        this.totalReleaseBlogNum = res.data.data.allNum;
        this.revenueComparisonLine.series[0].data = res.data.data.blogNumNow;
        this.revenueComparisonLine.series[1].data = res.data.data.blogNumLast;
        this.$store.commit('updateBlogReleaseNum', this.totalReleaseBlogNum);
      }
    }).catch(err => {
      console.error("err = ", err)
    })
    // 获取帖子的相关统计信息
    getCardStatus().then(res => {
      if (res.data.code == 200) {
        this.totalPostLike = res.data.data.postLikesTotal;
        this.totalPublished = res.data.data.postPublishedTotal;
        this.subscribersGained.series[0].data = res.data.data.postLikes;
        this.ordersRecevied.series[0].data = res.data.data.postPublished;
        this.$store.commit('updatePostReleaseNum', this.totalPublished);
      }
    }).catch(err => {
      console.log("err = ", err)
    })
  }
}
</script>

<style lang="scss">
#dashboard-analytics {
  .greet-user{
    position: relative;
    .decore-left{
      position: absolute;
      left:0;
      top: 0;
    }
    .decore-right{
      position: absolute;
      right:0;
      top: 0;
    }
  }

  @media(max-width: 576px) {
    .decore-left, .decore-right{
      width: 140px;
    }
  }
}
</style>
