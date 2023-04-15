import request from '@/network/request'
// 查询操作日志列表
export function list(query) {
  return request({
    url: '/admin/operlog/list',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function delOperlog(operateIds) {
  return request({
    url: '/admin/operlog/deleteBatchOperateLog',
    method: 'post',
    data: {
      operateIds: operateIds
    }
  })
}

// 清空操作日志
export function cleanOperlog() {
  return request({
    url: '/admin/operlog/clean',
    method: 'get'
  })
}
