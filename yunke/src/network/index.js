import request from "./request";
// 封装axios请求函数，并用export导出
// 登录请求
export function doLogin(datas, redisKey) {
  return request({
    url: "/user/login",
    method: "post",
    data: datas,
    headers: {
      'Redis-Key': redisKey
    }
  })
}

// 获取验证码
export function getCaptcha() {
  return request({
    url: "/user/verifyCode",
    method: 'get',
    responseType: 'blob' // 这里特殊注明返回格式是文件流
  })
}

// 发送注册请求
export function doRegister(datas, redisKey) {
  return request({
    url: "/user/signUp",
    method: 'post',
    data: datas,
    headers: {
      'Redis-Key': redisKey
    }
  })
}

// 获取用户所有信息
export function getAllUserInfo() {
  return request({
    url: "/user/getUserInfo",
    method: "post",
  })
}

// 更新用户信息
export function doModifyUserInfo(datas) {
  return request({
    url: "/user/modifyInfo",
    method: "post",
    data: datas
  })
}

// 用户更新密码
export function doUpdatePassword(datas) {
  return request({
    url: "/user/fix/password",
    method: "post",
    data: datas
  })
}

// 发送忘记密码请求
export function doForgetPassword(datas) {
  return request({
    url: "/user/resetPassword",
    method: "post",
    data: datas
  })
}
// 重置密码
export function doResetPassword(datas) {
  return request({
    url: "/user/verifyResetPassword",
    method: "post",
    data: datas
  })
}

// 分页查询帖子信息
export function getPostByPage(datas) {
  return request({
    url: "/post/list",
    method: 'get',
    params: datas
  })
}

// 点赞帖子请求
export function doLikePost(datas) {
  return request({
    url: '/post/like',
    method: 'post',
    data: datas
  })
}

// 查询活跃达人
export function getActivePerson(datas) {
  return request({
    url: '/post/talent',
    method: 'get',
    params: datas
  })
}

// 发表评论
export function doReleaseComment(datas) {
  return request({
    url: "/comment/add",
    method: 'post',
    data: datas
  })
}

// 获取全部个人的帖子
export function getPersonalPostByNum() {
  return request({
    url: "/post/listme",
    method: 'get',
  })
}

// 批量删除用户帖子信息
export function doDeleteMultiPosts(datas) {
  return request({
    url: "/post/delete",
    method: "post",
    data: datas
  })
}

// 发布帖子
export function doReleasePost(datas) {
  return request({
    url: '/post/add',
    method: 'post',
    data: datas
  })
}

// 获取好友列表
export function getAllContactPerson() {
  return request({
    url: '/conversation/getSimpleInformation',
    method: 'post',
  })
}

// 请求与对应好友的所有聊天信息
export function getAllChatMessage(datas) {
  return request({
    url: '/conversation/getAllMessage',
    method: 'post',
    params: datas
  })
}

// 将消息设置为未读消息
export function doUpdateMessageStatus(datas) {
  return request({
    url: '/conversation/setComplete',
    method: 'post',
    params: datas
  })
}

// 添加好友
export function doAddFriend(datas) {
  return request({
    url: "/conversation/addFriend",
    method: 'post',
    params: datas
  })
}

// 分页查询信息
export function getInfoByNum(datas) {
  return request({
    url: '/info/list',
    method: 'get',
    params: datas
  })
}

// 获取用户发布的信息
export function getMyReleaseInfo() {
  return request({
    url: '/info/list/me',
    method: 'get',
  })
}

// 用户删除发布的信息
export function doDeleteInfo(datas) {
  return request({
    url: '/info/delete',
    method: 'delete',
    data: datas
  })
}

// 用户发布信息
export function doReleaseInfo(datas) {
  return request({
    url: '/info/add',
    method: 'post',
    data: datas
  })
}

// 更新用户信息的状态
export function doUpdateInfoStatus(datas) {
  return request({
    url: '/info/update',
    method: 'post',
    data: datas
  })
}


// 上传图片
export function doUploadImage(datas) {
  return request({
    url: '/file/upload',
    method: 'post',
    data: datas,
    headers: {'Content-Type': 'multipart/form-data'}
  })
}

// 发布文章
export function doReleaseArticle(datas) {
  return request({
    url: '/blog/publishBlog',
    method: 'post',
    params: datas
  })
}

// 根据条件获取文章
export function getArticleByCondition(datas) {
  return request({
    url: '/blog/searchBlog',
    method: 'post',
    params: datas
  })
}
// 获取文章所有的分类
export function getAllArticleType() {
  return request({
    url: '/blog/getAllCategory',
    method: 'post',
  })
}

// 删除文章
export function doDeleteBlog(datas) {
  return request({
    url: '/blog/deleteBlog',
    method: 'post',
    params: datas
  })
}

// 获取用户的所有任务
export function getUserAllTask() {
  return request({
    url: '/task/list',
    method: 'get',
  })
}

// 添加或者修改任务
export function doUpdateOrAddTask(datas) {
  return request({
    url: '/task/add',
    method: 'post',
    data: datas
  })
}

// 删除任务
export function doDeleteTask(datas) {
  return request({
    url: '/task/delete',
    method: 'delete',
    data: datas
  })
}

// 获取日历模块所有的事件
export function getAllCalendarEvent() {
  return request({
    url: '/calendar/list',
    method: 'get'
  })
}

// 更新日历事件
export function doUpdateCalendarEvent(datas) {
  return request({
    url: '/calendar/add',
    method: 'post',
    data: datas
  })
}

// 删除日历事件
export function doDeleteCalendarEvent(datas) {
  return request({
    url: '/calendar/delete',
    method: 'delete',
    data: datas
  })
}

// 分页获取新闻
export function getNewsByNum(datas) {
  return request({
    url: '/news/list',
    method: 'get',
    params: datas
  })
}

// 获取新闻的所有标签
export function getAllNewsTag() {
  return request({
    url: '/news/tags',
    method: 'get',
  })
}

// 根据新闻id获取新闻详情
export function getNewsById(newsId) {
  return request({
    url: '/news/list/' + newsId,
    method: 'get'
  })
}

// 获取通告
export function getAllNotice(datas) {
  return request({
    url: '/notice/list',
    method: 'get',
    params: datas
  })
}

// 获取系统在线人数和总人数
export function getOnlineNum() {
  return request({
    url: '/user/getOnlineCount',
    method: 'get'
  })
}

// 获取用户的博客数
export function getUserBlogNum() {
  return request({
    url: '/blog/getBlogNum',
    method: 'get'
  })
}

// 获取帖子点赞量、帖子发布量以及近30天的数据
export function getCardStatus() {
  return request({
    url: '/post/like',
    method: 'get'
  })
}

// 获取总的信息发布量
export function getInfoNum() {
  return request({
    url: '/info/total',
    method: 'get'
  })
}

// 删除好友
export function doDeleteContactPerson(datas) {
  return request({
    url: '/conversation/deleteDialog',
    method: 'post',
    params: datas
  })
}