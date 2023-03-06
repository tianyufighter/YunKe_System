import axios from 'axios'
import Vue from "vue";

// 全局默认配置
let baseURL='/api'
// // 判断开发环境(一般用于本地代理)
// if (process.env.NODE_ENV === 'development') { // 开发环境
//   baseURL = '/api' // 你设置的本地代理请求(跨域代理)
// } else { // 编译环境
//   if (process.env.type === 'test') { // 测试环境
//     baseURL = 'http://124.223.38.131:8080'
//   } else { // 正式环境
//     baseURL = 'http://124.223.38.131:8080'
//   }
// }

// 请求配置文件，axios的配置都在这里进行
export default function request(config) {
   // 1.创建axios的实例
   const instance = axios.create({
     baseURL: baseURL, // 后台服务地址
     timeout: 10000,
     responseType: "json",
   })
  // 2.axios的拦截器
  instance.interceptors.request.use(config => {
    // if (config.url !== "/user/verifyCode" &&
    if (
        config.url !== "/post/like" &&
        config.url !== '/conversation/setComplete') {
      Vue.prototype.$vs.loading({
        type: 'corners'
      })
    }
    let token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = token;
    }
    return config
  }, error => {
    return Promise.reject(error)
  });
  instance.interceptors.response.use((res) => {
    if (res.headers.authorization) {
      localStorage.setItem("token", res.headers.authorization);
    }
    Vue.prototype.$vs.loading.close();
    // 请求响应后拦截
    if (res.status === 200) {
      return Promise.resolve(res)
    } else if (res.data.code === 411) {
      function acceptAlert() {
        localStorage.clear();
        this.$vs.notify({
          color: 'primary',
          title: '接收',
          text: '跳转到登录界面'
        })
      }
      Vue.prototype.$vs.dialog({
        color: 'warning',
        title: `警告`,
        text: '登录状态已过期，请重新登录。',
        accept: acceptAlert
      })
      this.$router.push("/pages/login");
      // return Promise.reject(res)
    } else {
      return res;
    }
  }, (error) => {
    Vue.prototype.$vs.loading.close();
    return Promise.reject(error)
  })
  // 3.发送真正的网络请求
   return instance(config);
}