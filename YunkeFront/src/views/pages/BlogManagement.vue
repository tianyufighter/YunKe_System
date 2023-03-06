]<!--
  博客管理页面
 -->
<template>
    <div id="faq-page">
        <!-- JUMBOTRON -->
        <div class="faq-jumbotron">
            <div class="faq-jumbotron-content lg:p-32 md:p-24 sm:p-16 p-8 rounded-lg mb-base">
                <h1 class="mb-1 text-white">博客搜索中心</h1>
                <p class="text-white">快来搜索自己的博客内容吧！</p>
                <vs-input placeholder="搜索" v-model="faqSearchQuery" icon-pack="feather" icon="icon-search" size="large" class="w-full mt-6" icon-no-border />
            </div>
        </div>
        <div class="vx-row">
            <div class="vx-col w-full md:w-2/5 lg:w-1/4 rounded-lg">
                <vx-card>
                    <h4>博客分类</h4>
                    <ul class="faq-topics mt-4">
                        <li v-for="(item, index) in blogType" :key="index" class="p-2 font-medium flex items-center" @click="faqFilter = item.name">
                            <div class="h-3 w-3 rounded-full mr-2" :style="{backgroundColor: item.color}"></div><span class="cursor-pointer">{{ item.name }}</span>
                        </li>
                    </ul>
                </vx-card>
            </div>
          <!--博客列表-->
          <div class="vx-col w-full md:w-3/5 lg:w-3/4 mt-12 md:mt-0">
            <blog-item v-for="(blog, index) in filteredFaq" :key="blog.id" :blog="blog" :title="blog.articleTitle.length <= 60 ? blog.articleTitle : blog.articleTitle.slice(0, 60) + '...'" action-buttons @removeBlog="removeBlog">
              <p class="mb-1">{{ blog.articleText.length <= 255 ? blog.articleText : blog.articleText.slice(0, 255) + '...'}}</p>
            </blog-item>
          </div>
        </div>
    </div>
</template>

<script>
import BlogItem from "./BlogItem";
import {doDeleteBlog, getAllArticleType, getArticleByCondition} from "../../network";
export default{
    data() {
        return {
            faqSearchQuery: '',
            faqFilter: '所有',
            blogType: [],
            blogs: [],
        }
    },
    computed: {
        filteredFaq() {
            return this.blogs.filter((faq) => {
                if (this.faqFilter == '所有') return (faq.articleTitle.includes(this.faqSearchQuery) || faq.articleText.includes(this.faqSearchQuery));
                for (let i = 0; i < this.blogType.length; i++) {
                  if (this.faqFilter == faq.category) {
                    return (faq.articleTitle.includes(this.faqSearchQuery) || faq.articleText.includes(this.faqSearchQuery));
                  }
                }
            });
        }
    },
    methods: {
      getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min)) + min;
      },
      // 删除博客
      removeBlog(blogId) {
        doDeleteBlog({
          blogId: blogId
        }).then(res => {
          if (res.data.code == 200) {
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
              console.log("err = ", err);
            })
            this.$vs.notify({
              color: 'danger',
              title: 'Deleted Blog',
              text: '这篇博客已经成功删除'
            })
          }
        }).catch(err => {
          console.log("err = ", err)
        })
      }
    },
    components: {
      BlogItem
    },
    mounted() {
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
          this.blogs = []
          getArticleByCondition({
            pattern: null,
            category: null
          }).then(res => {
            if (res.data.code == 200) {
              this.blogs = res.data.data;
            }
          }).catch(err => {
            console.log("err = ", err);
          })
        }
      }).catch(err => {
        console.error(err);
      })

    }
}
</script>

<style lang="scss">
#faq-page {
    .faq-jumbotron-content {
        background-image: url('../../assets/images/pages/faq.jpg');
        background-size: cover;
    }

    .faq-bg {
        background-color: #fff;
    }   
}
</style>