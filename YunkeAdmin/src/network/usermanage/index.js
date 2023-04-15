import request from '@/network/request'

/**
 * 用户登录系统
 * @param data 包含邮箱和密码的数据
 * @returns {*} 返回登录的结果
 */
export function loginSystem(data) {
  return request({
    url: '/admin/user/login',
    method: 'post',
    data: data
  })
}
// 获取所有的用户
export function getAllUserByPage(pageNum, pageSize) {
  return request({
    url: "/admin/user/allUserByPage",
    method: 'get',
    params: {
      pageNum: pageNum,
      pageSize: pageSize
    }
  })
}
// 添加新用户
export function addUser(user) {
  return request({
    url: "/admin/user/addUser",
    method: 'post',
    data: user
  })
}
// 根据用户id查询用户信息
export function getUserById(userId) {
  return request({
    url: "/admin/user/queryUserById",
    method: 'get',
    params: {
      id: userId
    }
  })
}
// 根据用户id更新用户信息
export function updateUserInfoById(user) {
  return request({
    url: '/admin/user/updateUser',
    method: 'post',
    data: user
  })
}

// 根据用户id重置用户密码
export function resetUserPassword(userId, password) {
  return request({
    url: '/admin/user/resetPassword',
    method: 'post',
    data: {
      id: userId,
      password: password
    }
  })
}

// 根据用户id删除用户信息
export function deleteUser(userId) {
  return request({
    url: '/admin/user/deleteUser',
    method: 'get',
    params: {
      id: userId
    }
  })
}

// 根据条件动态查询用户
export function getUserByCondition(userQuery) {
  return request({
    url: '/admin/user/queryUserByCondition',
    method: 'post',
    data: userQuery
  })
}
