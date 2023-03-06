package com.titos.personalmanagement.cache.userinfo.impl;

import com.titos.personalmanagement.cache.userinfo.UserInfoCache;
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
public class UserInfoCacheImpl implements UserInfoCache {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String cacheInfo(User user) {
        Assert.notNull(user, "User information can't be null");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(uuid, user);
        redisTemplate.expire(uuid, 30, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public User getInfoByKey(String key) {
        Assert.notNull(key, "key can't be null");
        User user = (User) redisTemplate.opsForValue().get(key);
        return user;
    }
}
