<!--
  博客详情界面
-->
<template>
    <div id="knowledge-base-article">
      <div class="vx-row">
            <div class="vx-col w-full">
                <vx-card>
                    <div class="article-title mb-6">
                        <h1>{{blog != null ? blog.articleTitle : ''}}</h1>
                        <p class="mt-1">发布时间: <span>{{blog != null ? blog.createTime : ''}}</span></p>
                    </div>
                    <!--博客内容-->
                    <div id="blog-content" v-html="blog != null ? blog.articleContent : ''"></div>
                  <template slot="footer">
                      <div class="flex items-center justify-between">
                          <router-link to="/apps/knowEverything/BlogPlatform" class="flex items-center"><feather-icon icon="ChevronsLeftIcon" class="mr-2" svgClasses="h-4 w-4"></feather-icon> 返回</router-link>
                      </div>
                  </template>
                </vx-card>

            </div>
      </div>
      <br>
      <div class="vx-row">
        <div class="vx-col w-full">
          <vx-card>
            <!-- 博客的评论 -->
            <b-col
                id="blogComment"
                cols="12"
                class="mt-2"
            >
              <h6 class="section-label text-primary text-lg">
                评论区
              </h6>
              <!-- POST ACTION DATA -->
              <div>
                <div class="comments-container mt-4">
                  <ul class="user-comments-list">
                    <li class="commented-user flex items-center mb-4" v-for="comment in commentList" :key="comment.id">
                      <div class="mr-3"><vs-avatar class="m-0" src="https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg" size="30px" /></div>
                      <div class="leading-tight">
                        <p class="font-medium">Titos</p>
                        <span class="text-xs">{{ comment.content }}</span>
                      </div>
                      <div class="ml-auto">
                        <div class="flex">
                          <span class="text-primary">{{comment.createTime.replace("T", " ")}}</span>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <span class="flex justify-end" @click="loadMoreComment" v-if="commentList.length != totalComment">
                    <a class="inline-flex items-center text-sm" href="javascript:;"><span>加载更多</span> <feather-icon icon="ChevronsRightIcon" svgClasses="h-4 w-4"></feather-icon></a>
                  </span>
                </div>
                <!--发表评论的输入框-->
                <div class="post-comment">
                  <vs-textarea label="comment" class="mb-2" v-model="commentContent" />
                  <vs-button size="small" @click="releaseBlogComment">发表评论</vs-button>
                </div>
              </div>
            </b-col>
            <!--/ 博客评论 -->
          </vx-card>
        </div>
      </div>

    </div>
</template>

<script>
import {marked} from "marked";
import {BCard, BMedia, BMediaAside, BAvatar, BMediaBody, BCardText, BLink, BCol} from "bootstrap-vue";
import {getBlogDetail, getBlogComment, releaseBlogComment} from "@network/blog";

export default{
    components: {
      BCard, BMedia, BMediaAside, BAvatar, BMediaBody, BCardText, BLink, BCol
    },
    data() {
        return {
            comments: [
              {id: 1, username: 'zhangsan', createTime: '2020-11-12 23:23:00', content: '写得很好', headImage: 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg'},
              {id: 2, username: 'lisi', createTime: '2020-11-12 23:23:00', content: '写得还不错', headImage: 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg'},
              {id: 3, username: 'wangwu', createTime: '2020-11-12 23:23:00', content: '6666', headImage: 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg'},
              {id: 4, username: 'zhaoliu', createTime: '2020-11-12 23:23:00', content: 'hehehehe', headImage: 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg'}
            ],
            article: {
                title: 'Dessert halvah carrot cake sweet?',
                lastUpdated: 'Mon Dec 10 2018 07:45:55 GMT+0000 (GMT)',
                img: 'kb-article.jpg',
                topics: ['Pastry jelly chocolate bar caramels', 'Donut chupa chups oat cake', 'Marshmallow icing topping toffee caramels dessert carrot cake']
            },
            blog: null,
            pageNum: 1, // 评论的页数
            pageSize: 3, // 每页显示的评论数量
            commentList: [], // 该博客对应的评论信息
            totalComment: 0, // 总的评论数量
            commentContent: '', // 评论框中输入评论的内容
        }
    },
    computed: {

    },
    methods: {
      // 发表评论
      releaseBlogComment() {
        if (this.commentContent == '') {
          this.$vs.notify({
            title:'消息提示',
            text:'你还没有输入评论信息，不能发表!',
            color:'warning',
            position:'top-center'})
        } else {
          releaseBlogComment({
            userId: 2,
            blogId: this.blog.id,
            content: this.commentContent,
          }).then(res => {
            if (res.data.code == 200) {
              this.commentContent = '';
              this.reloadBlogComment();
              this.$vs.notify({
                title:'消息提示',
                text:'评论发布成功',
                color:'success',
                position:'top-center'})
            } else {
              this.$vs.notify({
                title:'错误提示',
                text:'评论未能发布成功',
                color:'danger',
                position:'top-center'})
            }
          }).catch(err => {
            this.$vs.notify({
              title:'错误提示',
              text:'评论未能发布成功',
              color:'danger',
              position:'top-center'})
          })
        }
      },
      // 加载更多的评论
      loadMoreComment() {
          this.pageNum = this.pageNum + 1; //评论页数+1
        getBlogComment({
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          blogId: this.blog.id
        }).then(res => {
          for (let i = 0; i < res.data.data.list.length; i++) {
            this.commentList.push(res.data.data.list[i]);
          }
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'博客的评论信息查询失败',
            color:'danger',
            position:'top-center'})
        })
      },
      // 重新加载评论信息
      reloadBlogComment() {
        this.pageNum = 1;
        getBlogComment({
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          blogId: this.blog.id
        }).then(res => {
          this.totalComment = res.data.data.total;
          this.commentList = res.data.data.list;
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'博客的评论信息查询失败',
            color:'danger',
            position:'top-center'})
        })
      }
    },
    mounted() {
      // 根据文章的id获取文章的详细信息
      getBlogDetail({
        blogId: this.$route.params.id
      }).then(res => {
        this.blog = res.data.data;
        // 查询文章的评论信息
        getBlogComment({
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          blogId: this.blog.id
        }).then(res => {
          this.totalComment = res.data.data.total;
          this.commentList = res.data.data.list;
        }).catch(err => {
          this.$vs.notify({
            title:'错误提示',
            text:'博客的评论信息查询失败',
            color:'danger',
            position:'top-center'})
        })
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'博客信息查询失败',
          color:'danger',
          position:'top-center'})
      })
    }
}
</script>
<style lang="scss" scoped>
  @import "@/assets/scss/vuesax/pages/profile.scss";
  #blog-content >>> img {
    max-width: 800px;
  }
</style>
