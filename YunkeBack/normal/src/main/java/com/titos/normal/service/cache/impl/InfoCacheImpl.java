package com.titos.normal.service.cache.impl;

import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.normal.service.cache.InfoCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class InfoCacheImpl implements InfoCache {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String cacheInfo(CacheInfoVO cacheInfoVO) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(uuid, cacheInfoVO.getObj());
        redisTemplate.expire(uuid, cacheInfoVO.getTimeout(), cacheInfoVO.getTimeUnit());
        return uuid;
    }

    @Override
    public Object getInfoByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
