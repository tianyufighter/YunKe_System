package com.titos.normal.service.cache;


import com.titos.info.cache.vo.CacheInfoVO;

/**
 * 使用redis对信息进行存取
 */
public interface InfoCache {
    /**
     * 缓存对象obj到redis中并生成uuid作为key
     * @param cacheInfoVO 缓存信息
     * @return key
     */
    String cacheInfo(CacheInfoVO cacheInfoVO);

    /**
     * 根据key来获取T类型的对象
     * @param key 键
     * @return redis中该键所对应的数据
     */
    Object getInfoByKey(String key);
}
