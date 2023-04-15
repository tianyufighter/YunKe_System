import request from '@/network/request'

/**
 * 获取帖子信息
 * @param data 封装的查询条件
 * @returns {*}
 */
export function getPost(data) {
  return request({
    url: '/admin/post/getPostByCondition',
    method: 'post',
    data: data
  })
}

/**
 * 更新帖子信息
 * @param data
 * @returns {*}
 */
export function updatePost(data) {
  return request({
    url: '/admin/post/updatePost',
    method: 'post',
    data: data
  })
}

/**
 * 批量删除帖子
 * @param data
 */
export function deletePostBatch(data) {
  return request({
    url: '/admin/post/deletePost',
    method: 'post',
    data: {
      idList: data
    }
  })
}
