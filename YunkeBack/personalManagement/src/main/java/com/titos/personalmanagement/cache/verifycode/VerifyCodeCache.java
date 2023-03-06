package com.titos.personalmanagement.cache.verifycode;


/**
 * 使用redis对验证码进行存取
 * @author Titos
 */
public interface VerifyCodeCache {
    /**
     * 缓存User类型的对象到redis中并生成uuid作为key
     * @param verifyCode 验证码
     * @return key
     */
    String cacheVerifyCode(String verifyCode);

    /**
     * 根据key来获取验证码
     * @param key 键
     * @return redis中该键所对应的数据
     */
    String getCodeByKey(String key);
}
