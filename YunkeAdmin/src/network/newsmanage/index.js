import request from '@/network/request'

/**
 * 根据条件分页查询新闻信息
 * @param data
 * @returns {*}
 */
export function getNews(data) {
  return request({
    url: '/admin/news/newsList',
    method: 'post',
    data: data
  })
}

/**
 * 更新新闻信息
 * @param data
 * @returns {*}
 */
export function updateNews(data) {
  return request({
    url: '/admin/news/updateNews',
    method: 'post',
    data: data
  })
}

/**
 * 根据新闻id删除新闻信息
 * @param data
 * @returns {*}
 */
export function deleteNews(data) {
  return request({
    url: '/admin/news/deleteNews',
    method: 'post',
    data: {
      idList: data
    }
  })
}

/**
 * 上传新闻封面图片
 * @param datas
 * @returns {*}
 */
export function doUploadImage(datas) {
  return request({
    url: '/common/file/upload',
    method: 'post',
    data: datas,
    headers: {'Content-Type': 'multipart/form-data'}
  })
}

/**
 * 添加新闻信息
 * @param datas
 * @returns {*}
 */
export function addNews(datas) {
  return request({
    url: '/admin/news/addNews',
    method: 'post',
    data: datas
  })
}
