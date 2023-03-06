package com.titos.personalmanagement.cache.verifycode.impl;

import com.titos.personalmanagement.cache.verifycode.VerifyCodeCache;
import com.titos.personalmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis中用户数据存取的实现
 * @author Titos
 */
@Repository
public class VerifyCodeCacheImpl implements VerifyCodeCache {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String cacheVerifyCode(String verifyCode) {
        Assert.notNull(verifyCode, "verifyCode can't be null");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(uuid, verifyCode);
        redisTemplate.expire(uuid, 2, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public String getCodeByKey(String key) {
        Assert.notNull(key, "key can't be null");
        String code = (String) redisTemplate.opsForValue().get(key);
        return code;
    }
}
