module.exports = {
  // devServer 配置本地服务器
  devServer: {
    // 代理地址
    proxy: {
      // 当本地服务器碰到有'/api'的参数地址的时候，就开始去代理
      '/api': { // 这个要与request.js文件中的baseURL值对应
        // 真正去请求数据的地址
        target: "http://124.223.38.131:80/api",
        // target: "http://localhost:8080/",
        // 是否跨域
        changeOrigin: true,
        // 重写路径
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
