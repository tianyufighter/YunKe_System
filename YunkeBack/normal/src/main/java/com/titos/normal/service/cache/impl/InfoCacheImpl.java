package com.titos.normal.service.cache.impl;

import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.normal.service.cache.InfoCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class InfoCacheImpl implements InfoCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String cacheInfo(CacheInfoVO cacheInfoVO) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String key = cacheInfoVO.getPrefixKey() + uuid;
        redisTemplate.opsForValue().set(key, cacheInfoVO.getObj());
        redisTemplate.expire(key, cacheInfoVO.getTimeout(), cacheInfoVO.getTimeUnit());
        return key;
    }

    @Override
    public Object getInfoByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
