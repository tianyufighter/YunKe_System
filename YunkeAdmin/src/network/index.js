import request from "./request";



// 封装axios请求函数，并用export导出
// 刷新敏感词库
export function refreshSensitiveLibrary() {
  return request({
    url: '/admin/sensitive/refresh',
    method: 'get'
  })
}
