import request from '@/network/request'

/**
 * 获取博客信息
 * @param data
 * @returns {*}
 */
export function getBlog(data) {
  return request({
    url: '/admin/blog/getBlogByCondition',
    method: 'post',
    data: data
  })
}

/**
 * 更新博客信息
 * @param data
 * @returns {*}
 */
export function updateBlog(data) {
  return request({
    url: '/admin/blog/updateBlog',
    method: 'post',
    data: data
  })
}

/**
 * 批量删除博客
 * @param data
 */
export function deleteBlogBatch(data) {
  return request({
    url: '/admin/blog/deleteBlog',
    method: 'post',
    data: {
      idList: data
    }
  })
}
