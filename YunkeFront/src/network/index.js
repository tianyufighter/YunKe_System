import request from "./request";
// 封装axios请求函数，并用export导出
// 登录请求
export function doLogin(datas, redisKey) {
  return request({
    url: "/personManagement/login",
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
    url: "/personManagement/verifyCode",
    method: 'get',
    responseType: 'blob' // 这里特殊注明返回格式是文件流
  })
}

// 发送注册请求
export function doRegister(datas, redisKey) {
  return request({
    url: "/personManagement/signUp",
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
    url: "/personManagement/getUserInfo",
    method: "post",
  })
}

// 更新用户信息
export function doModifyUserInfo(datas) {
  return request({
    url: "/personManagement/modifyInfo",
    method: "post",
    data: datas
  })
}

// 用户更新密码
export function doUpdatePassword(datas) {
  return request({
    url: "/personManagement/fix/password",
    method: "post",
    data: datas
  })
}

// 发送忘记密码请求
export function doForgetPassword(datas) {
  return request({
    url: "/personManagement/resetPassword",
    method: "post",
    data: datas
  })
}
// 重置密码
export function doResetPassword(datas) {
  return request({
    url: "/personManagement/verifyResetPassword",
    method: "post",
    data: datas
  })
}

// 分页查询帖子信息
export function getPostByPage(datas) {
  return request({
    url: "/sharePlatform/post/list",
    method: 'get',
    params: datas
  })
}

// 点赞帖子请求
export function doLikePost(datas) {
  return request({
    url: '/sharePlatform/post/like',
    method: 'post',
    data: datas
  })
}

// 查询活跃达人
export function getActivePerson(datas) {
  return request({
    url: '/sharePlatform/post/talent',
    method: 'get',
    params: datas
  })
}

// 发表评论
export function doReleaseComment(datas) {
  return request({
    url: "/sharePlatform/comment/add",
    method: 'post',
    data: datas
  })
}

// 获取全部个人的帖子
export function getPersonalPostByNum() {
  return request({
    url: "/sharePlatform/post/listme",
    method: 'get',
  })
}

// 批量删除用户帖子信息
export function doDeleteMultiPosts(datas) {
  return request({
    url: "/sharePlatform/post/delete",
    method: "post",
    data: datas
  })
}

// 发布帖子
export function doReleasePost(datas) {
  return request({
    url: '/sharePlatform/post/add',
    method: 'post',
    data: datas
  })
}

// 获取好友列表
export function getAllContactPerson() {
  return request({
    url: '/conversation/message/getSimpleInformation',
    method: 'post',
  })
}

// 请求与对应好友的所有聊天信息
export function getAllChatMessage(datas) {
  return request({
    url: '/conversation/message/getAllMessage',
    method: 'post',
    params: datas
  })
}

// 将消息设置为未读消息
export function doUpdateMessageStatus(datas) {
  return request({
    url: '/conversation/message/setComplete',
    method: 'post',
    params: datas
  })
}

// 添加好友
export function doAddFriend(datas) {
  return request({
    url: "/conversation/contactperson/addFriend",
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


// 编辑博客文章时上传图片
export function doUploadImage(datas) {
  return request({
    url: '/common/file/upload',
    method: 'post',
    data: datas,
    headers: {'Content-Type': 'multipart/form-data'}
  })
}

// 发布文章
export function doReleaseArticle(datas) {
  return request({
    url: '/technicalArchive/blog/publishBlog',
    method: 'post',
    data: datas,
  })
}

// 根据条件获取文章
export function getArticleByCondition(datas) {
  return request({
    url: '/technicalArchive/blog/searchBlog',
    method: 'post',
    params: datas
  })
}
// 获取文章所有的分类
export function getAllArticleType() {
  return request({
    url: '/technicalArchive/blog/getAllCategory',
    method: 'post',
  })
}

// 删除文章
export function doDeleteBlog(datas) {
  return request({
    url: '/technicalArchive/blog/deleteBlog',
    method: 'post',
    params: datas
  })
}

// 获取用户的所有任务
export function getUserAllTask() {
  return request({
    url: '/sharePlatform/task/list',
    method: 'get',
  })
}

// 添加或修改任务
export function doAddOrUpdateTask(datas) {
  return request({
    url: '/sharePlatform/task/addOrUpdate',
    method: 'post',
    data: datas
  })
}

// 删除任务
export function doDeleteTask(datas) {
  return request({
    url: '/sharePlatform/task/delete',
    method: 'delete',
    data: datas
  })
}

// 获取日历模块所有的事件
export function getAllCalendarEvent() {
  return request({
    url: '/sharePlatform/calendar/list',
    method: 'get'
  })
}

// 更新日历事件
export function doUpdateCalendarEvent(datas) {
  return request({
    url: '/sharePlatform/calendar/add',
    method: 'post',
    data: datas
  })
}

// 删除日历事件
export function doDeleteCalendarEvent(datas) {
  return request({
    url: '/sharePlatform/calendar/delete',
    method: 'delete',
    data: datas
  })
}

// 分页获取新闻
export function getNewsByNum(datas) {
  return request({
    url: '/sharePlatform/news/list',
    method: 'get',
    params: datas
  })
}

// 获取新闻的所有标签
export function getAllNewsTag() {
  return request({
    url: '/sharePlatform/news/tags',
    method: 'get',
  })
}

// 根据新闻id获取新闻详情
export function getNewsById(newsId) {
  return request({
    url: '/sharePlatform/news/list/' + newsId,
    method: 'get'
  })
}

// 获取通告
export function getAllNotice(datas) {
  return request({
    url: '/sharePlatform/notice/list',
    method: 'get',
    params: datas
  })
}

// 获取系统在线人数和总人数
export function getOnlineNum() {
  return request({
    url: '/personManagement/getOnlineCount',
    method: 'get'
  })
}

// 获取用户的博客数
export function getUserBlogNum() {
  return request({
    url: '/technicalArchive/blog/getBlogNum',
    method: 'get'
  })
}

// 获取帖子点赞量、帖子发布量以及近30天的数据
export function getCardStatus() {
  return request({
    url: '/sharePlatform/post/like',
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
    url: '/conversation/message/deleteDialog',
    method: 'post',
    params: datas
  })
}
