import request from '@/network/request'

// 查询缓存详细
export function getCache() {
  return request({
    url: '/admin/cache',
    method: 'get'
  })
}

// 查询缓存名称列表
export function listCacheName() {
  return request({
    url: '/admin/cache/getNames',
    method: 'get'
  })
}

// 查询缓存键名列表
export function listCacheKey(cacheName) {
  return request({
    url: '/admin/cache/getKeys',
    method: 'get',
    params: {
      cacheName: cacheName
    }
  })
}

// 查询缓存内容
export function getCacheValue(cacheName, cacheKey) {
  return request({
    url: '/admin/cache/getValue',
    method: 'get',
    params: {
      cacheName: cacheName,
      cacheKey: cacheKey
    }
  })
}

// 清理指定名称缓存
export function clearCacheName(cacheName) {
  return request({
    url: '/admin/cache/clearCacheName',
    method: 'get',
    params: {
      cacheName: cacheName
    }
  })
}

// 清理指定键名缓存
export function clearCacheKey(cacheKey) {
  return request({
    url: '/admin/cache/clearCacheKey',
    method: 'get',
    params: {
      cacheKey: cacheKey
    }
  })
}

// 清理全部缓存
export function clearCacheAll() {
  return request({
    url: '/admin/cache/clearCacheAll',
    method: 'get'
  })
}
