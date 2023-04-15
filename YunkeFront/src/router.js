/*=========================================================================================
  File Name: router.js
  Description: Routes for vue-router. Lazy loading is enabled.
  Object Strucutre:
                    path => router path
                    name => router name
                    component(lazy loading) => component to load
                    meta : {
                      rule => which user can have access (ACL)
                      breadcrumb => Add breadcrumb to specific page
                      pageTitle => Display title besides breadcrumb
                    }
==========================================================================================*/


import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    scrollBehavior () {
        return { x: 0, y: 0 }
    },
    routes: [
        {
            path: '',
            component: () => import('./layouts/main/Main.vue'),
            children: [
                {
                    path: '/',
                    redirect: '/home/personalData'
                },
                //个人数据页面
                {
                    path: '/home/personalData',
                    name: 'PersonalData',
                    component: () => import('./views/pages/PersonalData.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                //社区页面
                {
                    path: '/home/community',
                    name: 'Community',
                    component: () => import('./views/pages/Post.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: 'community', active: true},
                        ],
                        pageTitle: '社区',
                        rule: 'admin'
                    },
                },
                // 我的帖子页面
                {
                    path: '/home/myPost',
                    name: 'MyPost',
                    component: () => import('./views/pages/MyPost.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                // 聊天页面
                {
                    path: '/apps/chat',
                    name: 'Chat',
                    component: () => import('./views/apps/chat/Chat.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                // 任务页面
                {
                    path: '/apps/todo',
                    name: 'Todo',
                    component: () => import('./views/apps/todo/Todo.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                // 日历页面
                {
                    path: '/apps/calendar/vue-fullcalendar',
                    name: 'Calender',
                    component: () => import('./views/apps/calendar/FullCalendar.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                // 博客平台页面
                {
                    path: '/apps/knowEverything/BlogPlatform',
                    name: 'BlogPlatform',
                    component: () => import('@/views/pages/BlogPlatform.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用'},
                            { title: '博客专区'},
                            { title: '博客平台', active: true },
                        ],
                        pageTitle: '博客平台',
                        rule: 'editor'
                    },
                },
                // 博客详情界面
                {
                    path: '/apps/knowEverything/BlogDetails/:id',
                    name: 'BlogDetails',
                    component: () => import('@/views/pages/BlogDetail.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用'},
                            { title: '博客专区'},
                            { title: '博客平台', active: true },
                            { title: '博客详情', active: true },
                        ],
                        pageTitle: '博客详情',
                        rule: 'editor'
                    },
                },
                // 个人博客管理界面
                {
                    path: '/apps/knowEverything/BlogManage',
                    name: 'BlogManage',
                    component: () => import('@/views/pages/BlogManage.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用'},
                            { title: '博客专区'},
                            { title: '博客管理', active: true },
                        ],
                        pageTitle: '博客管理',
                        rule: 'editor'
                    },
                },
                // 新闻页面
                {
                    path: '/apps/news',
                    name: 'News',
                    component: () => import('@/views/pages/News.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用' },
                            { title: '新闻', active: true },
                        ],
                        pageTitle: '新闻',
                        rule: 'editor'
                    },
                },
                // 博客编辑页面
                {
                    path: '/apps/blog-editor',
                    name: 'BlogEditor',
                    component: () => import('@/views/pages/Markdown.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用' },
                            { title: '编辑', active: true },
                        ],
                        pageTitle: '博客编辑',
                        rule: 'editor'
                    },
                },
                // 个人中心页面
                {
                    path: '/pages/profile',
                    name: 'PersonalCenter',
                    component: () => import('@/views/pages/PersonalCenter.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '个人中心', active: true },
                        ],
                        pageTitle: '个人中心',
                        rule: 'editor'
                    },
                },
                // 新闻详情页面
                {
                    path: '/apps/news/newsDetail',
                    name: 'newsDetail',
                    component: () => import('./views/pages/NewsDetail.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                // 博客详情页面
                {
                    path: '/pages/BlogManagement/blogDetail',
                    name: 'pageKnowledgeBaseCategoryQuestion',
                    component: () => import('@/views/pages/BlogDetail.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用' },
                            { title: '博客面板' },
                            { title: '博客管理', url: '/pages/BlogManagement' },
                            { title: '博客详情', active: true },
                        ],
                        pageTitle: '博客详情',
                        rule: 'editor',
                    },
                },
                // 关于界面
                {
                    path: '/pages/About',
                    name: 'about',
                    component: () => import('./views/pages/About.vue'),
                    meta: {
                        breadcrumb: [
                            { title: 'Home', url: '/' },
                            { title: '应用' },
                            { title: '关于', active: true },
                        ],
                        pageTitle: '关于',
                        rule: 'editor',
                    },
                },
            ],
        },
    // =============================================================================
    // FULL PAGE LAYOUTS
    // =============================================================================
        {
            path: '',
            component: () => import('@/layouts/full-page/FullPage.vue'),
            children: [
        // =============================================================================
        // PAGES
        // =============================================================================
                {
                    path: '/callback',
                    name: 'authCallback',
                    component: () => import('@/views/Callback.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/login',
                    name: 'pageLogin',
                    component: () => import('@/views/pages/Login.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/register',
                    name: 'pageRegister',
                    component: () => import('@/views/pages/Register.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/forgot-password',
                    name: 'pageForgotPassword',
                    component: () => import('@/views/pages/ForgotPassword.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/reset-password',
                    name: 'pageResetPassword',
                    component: () => import('@/views/pages/ResetPassword.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/lock-screen',
                    name: 'pageLockScreen',
                    component: () => import('@/views/pages/LockScreen.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/comingsoon',
                    name: 'pageComingSoon',
                    component: () => import('@/views/pages/ComingSoon.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/error-404',
                    name: 'pageError404',
                    component: () => import('@/views/pages/Error404.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/error-500',
                    name: 'pageError500',
                    component: () => import('@/views/pages/Error500.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/not-authorized',
                    name: 'pageNotAuthorized',
                    component: () => import('@/views/pages/NotAuthorized.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
                {
                    path: '/pages/maintenance',
                    name: 'pageMaintenance',
                    component: () => import('@/views/pages/Maintenance.vue'),
                    meta: {
                        rule: 'editor'
                    }
                },
            ]
        },
        // Redirect to 404 page, if no match found
        {
            path: '*',
            redirect: '/pages/error-404'
        }
    ],
})

router.afterEach(() => {
  // Remove initial loading
  const appLoading = document.getElementById('loading-bg')
    if (appLoading) {
        appLoading.style.display = "none";
    }
})

router.beforeEach((to, from, next) => {
    let token = localStorage.getItem("token");
    if (
        to.path === "/pages/login" ||
        to.path === "/pages/forgot-password" ||
        to.path === "/pages/error-404" ||
        to.path === "/pages/error-500" ||
        to.path === "/pages/register" ||
        to.path === "/callback" ||
        to.path === "/pages/comingsoon" ||
        to.path === "/pages/reset-password" ||
        (token != null && token !== "undefined")
    ) {
        return next();
    }
    next('/pages/login')
});

export default router
