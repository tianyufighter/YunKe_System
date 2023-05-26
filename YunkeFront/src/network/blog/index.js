import request from '@/network/request'

/**
 * 根据条件分页查询博客信息
 * @param data
 * @returns {*}
 */
export function getBlogByCondition(data) {
    return request({
        url: '/technicalArchive/blog/getBlogByCondition',
        method: 'post',
        data: data
    })
}

/**
 * 根据条件分页查询个人的博客信息
 * @param data
 */
export function getMyBlogByCondition(data) {
    return request({
        url: '/technicalArchive/blog/getMyBlogByCondition',
        method: 'post',
        data: data
    })
}

/**
 * 查询自己所有的博客
 */
export function getMyAllBlog() {
    return request({
        url: '/technicalArchive/blog/searchBlog',
        method: 'post'
    })
}

/**
 * 根据博客id获取博客的详细信息
 * @param data
 * @returns {*}
 */
export function getBlogDetail(data) {
    return request({
        url: 'technicalArchive/blog/getBlogDetail',
        method: 'post',
        params: data
    })
}

/**
 * 获取博客的评论信息
 */
export function getBlogComment(data) {
    return request({
        url: '/technicalArchive/comment/getBlogComment',
        method: 'post',
        data: data
    })
}

/**
 * 发布博客的评论信息
 */
export function releaseBlogComment(data) {
    return request({
        url: '/technicalArchive/comment/addBlogComment',
        method: 'post',
        data: data
    })
}

/**
 * 根据博客id更新博客信息
 * @param data
 * @returns {*}
 */
export function updateBlog(data) {
    return request({
        url: '/technicalArchive/blog/updateBlog',
        method: 'post',
        data: data
    })
}

/**
 * 更新博客的公开状态
 * @param data 待更新的信息
 * @returns {*}
 */
export function updateBlogStatus(data) {
    return request({
        url: '/technicalArchive/blog/updateBlogStatus',
        method: 'post',
        data: data
    })
}

/**
 * 批量删除博客
 * @param data
 * @returns {*}
 */
export function deleteBlogBatch(data) {
    return request({
        url: '/technicalArchive/blog/deleteBlogBatch',
        method: 'post',
        data: data
    })
}
