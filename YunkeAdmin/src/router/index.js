import Vue from 'vue'
import VueRouter from 'vue-router'

// Routes
import pages from './routes/pages'
import systemManage from '@/router/routes/system-manage'
import systemMonitor from '@/router/routes/system-monitor'
import homePage from '@/router/routes/home-page'
import dataManage from '@/router/routes/data-manage'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: '/admin/',
  scrollBehavior() {
    return { x: 0, y: 0 }
  },
  routes: [
    { path: '/', redirect: { name: 'home-page' } },
    ...homePage,
    ...systemManage,
    ...systemMonitor,
    ...dataManage,
    ...pages,
    {
      path: '*',
      redirect: 'error-404',
    },
  ],
})

router.beforeEach((to, _, next) => {
  if (to.path === "/pages/login") {
    return next();
  }
  const isLoggedIn = localStorage.getItem('userData') && localStorage.getItem('token');
  // 如果用户没有登录返回到登录页面
  if (isLoggedIn == null) return next({ name: 'auth-login' })
  next();
})

// ? For splash screen
// Remove afterEach hook if you are not using splash screen
router.afterEach(() => {
  // Remove initial loading
  const appLoading = document.getElementById('loading-bg')
  if (appLoading) {
    appLoading.style.display = 'none'
  }
})

export default router
