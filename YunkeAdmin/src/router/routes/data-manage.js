export default [
  {
    path: '/apps/datamanage/post',
    name: 'post-manage',
    component: () => import('@/views/apps/data-manage/post-manage/PostSetting.vue'),
  },
  {
    path: '/apps/datamanage/blog',
    name: 'blog-manage',
    component: () => import('@/views/apps/data-manage/blog-manage/BlogSetting.vue'),
  },
  {
    path: '/apps/datamanage/news',
    name: 'news-manage',
    component: () => import('@/views/apps/data-manage/news-manage/NewsSetting.vue')
  }
]
