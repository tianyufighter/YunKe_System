import request from '@/network/request'

// 查询公告列表
export function listNotice(query) {
  return request({
    url: '/admin/notice/list',
    method: 'get',
    params: query
  })
}

// 查询公告详细
export function getNotice(noticeId) {
  return request({
    url: '/admin/notice/getNoticeById',
    method: 'get',
    params: {
      noticeId
    }
  })
}

// 新增公告
export function addNotice(data) {
  return request({
    url: '/admin/notice/addNotice',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateNotice(data) {
  return request({
    url: '/admin/notice/updateNotice',
    method: 'post',
    data: data
  })
}

// 删除公告
export function delNotice(noticeId) {
  return request({
    url: '/admin/notice/deleteNotice',
    method: 'get',
    params: {
      noticeId
    }
  })
}
