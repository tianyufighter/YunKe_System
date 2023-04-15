import request from '@/network/request'

// 查询登录日志列表
export function list(query) {
  return request({
    url: '/admin/loginLog/list',
    method: 'get',
    params: query
  })
}

// 删除登录日志
export function delLogininfor(infoIds) {
  return request({
    url: '/admin/loginLog/deleteBatch',
    method: 'post',
    data: {
      ids: infoIds
    }
  })
}


// 清空登录日志
export function cleanLogininfor() {
  return request({
    url: '/admin/loginLog/clean',
    method: 'get'
  })
}
