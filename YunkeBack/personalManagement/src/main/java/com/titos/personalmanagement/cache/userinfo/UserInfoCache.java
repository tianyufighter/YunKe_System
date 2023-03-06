package com.titos.personalmanagement.cache.userinfo;

import com.titos.personalmanagement.model.User;

/**
 * 使用redis对用户数据进行存取
 * @author Titos
 */
public interface UserInfoCache {
    /**
     * 缓存User类型的对象到redis中并生成uuid作为key
     * @param user 用户对象
     * @return key
     */
    String cacheInfo(User user);

    /**
     * 根据key来获取T类型的对象
     * @param key 键
     * @return redis中该键所对应的数据
     */
    User getInfoByKey(String key);
}
