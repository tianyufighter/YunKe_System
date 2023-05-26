import axios from 'axios'
import Vue from "vue";
import {Message} from "element-ui";
import router from "@/router";

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
  // 2.axios的request拦截器
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
   // 响应拦截器
  instance.interceptors.response.use((res) => {
    if (res.headers.authorization) {
      localStorage.setItem("token", res.headers.authorization);
    }
    Vue.prototype.$vs.loading.close();
    // 请求响应后拦截
    if (res.status === 200) {
      return Promise.resolve(res)
    } else if (res.data.code === 411 || res.data.code == 402 || res.data.code == 412 || res.data.code == 413 || res.data.code == 414) {
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
    let {message} = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substr(message.length - 3) + "异常";
      // router.push("/pages/error-500");
      // return Promise.reject(error);
    }
    // Message({message: message, type: 'error', duration: 5 * 1000})
    Vue.prototype.$vs.notify({
      title:'错误提示',
      text: message,
      color:'danger',
      position:'top-center'})
    return Promise.reject(error)
  })
  // 3.发送真正的网络请求
   return instance(config);
}
