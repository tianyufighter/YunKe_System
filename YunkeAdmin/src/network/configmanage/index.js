import request from '@/network/request'

// 查询参数列表
export function listConfig(query) {
  return request({
    url: '/admin/config/list',
    method: 'get',
    params: query
  })
}

// 查询参数详细
export function getConfig(configId) {
  return request({
    url: '/admin/config/detail',
    method: 'get',
    params: {
      id: configId
    }
  })
}

// 根据参数键名查询参数值
export function getConfigKey(configKey) {
  return request({
    url: '/admin/config/configKey',
    method: 'get',
    params: {
      configKey
    }
  })
}

// 新增参数配置
export function addConfig(data) {
  return request({
    url: '/admin/config/addConfig',
    method: 'post',
    data: data
  })
}

// 修改参数配置
export function updateConfig(data) {
  return request({
    url: '/admin/config/updateConfig',
    method: 'post',
    data: data
  })
}

// 删除参数配置
export function delConfig(configId) {
  return request({
    url: '/admin/config/deleteConfig',
    method: 'get',
    params: {
      id: configId
    }
  })
}

// 刷新参数缓存
export function refreshCache() {
  return request({
    url: '/admin/config/refreshCache',
    method: 'get'
  })
}
