import request from '@/network/request'

// 获取服务信息
export function getServer() {
  return request({
    url: '/admin/server',
    method: 'get'
  })
}
