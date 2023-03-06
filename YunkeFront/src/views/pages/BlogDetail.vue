<!--
  博客详情界面
-->
<template>
    <div id="knowledge-base-article">
        <div class="vx-row">
            <div class="vx-col w-full">
                <vx-card>
                    <div class="article-title mb-6">
                        <h1>{{blog.articleTitle}}</h1>
                        <p class="mt-1">发布时间: <span>{{ blog.createTime | date(true) }}</span></p>
                    </div>
                    <!--博客内容-->
                    <div v-html="blog.articleContent">
                    </div>
                    <template slot="footer">
                        <div class="flex items-center justify-between">
                            <router-link to="/pages/BlogManagement" class="flex items-center"><feather-icon icon="ChevronsLeftIcon" class="mr-2" svgClasses="h-4 w-4"></feather-icon> 返回</router-link>
                        </div>
                    </template>
                </vx-card>
            </div>
        </div>
    </div>
</template>

<script>

export default{
    data() {
        return {
            article: {
                title: 'Dessert halvah carrot cake sweet?',
                lastUpdated: 'Mon Dec 10 2018 07:45:55 GMT+0000 (GMT)',
                img: 'kb-article.jpg',
                topics: ['Pastry jelly chocolate bar caramels', 'Donut chupa chups oat cake', 'Marshmallow icing topping toffee caramels dessert carrot cake']
            },
        }
    },
    computed: {
      blog() {
        return this.$store.state.blogDetail;
      }
    },
    methods: {},
    components: {},
    mounted() {
    }
}
</script>