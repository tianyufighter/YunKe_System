package com.titos.info.global.constant;

/**
 * 缓存的key常量
 */
public class CacheConstants {
    /**
     * 系统在线人数
     */
    public static final String ONLINE_PEOPLE_COUNT  = "online_people_count";

    /**
     * 用户注册时缓存用户数据对应的key的前缀
     */
    public static final String REGISTER_CACHE_USER_KEY = "register_cache_user:";
    /**
     * 重置密码时缓存用户数据对应的key的前缀
     */
    public static final String RESET_PASSWORD_CACHE_USER_KEY = "reset_password_cache_user:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 活跃达人(zSet)
     */
    public static final String ACTIVE = "active";

    /**
     * 点赞帖子的用户(set)
     */
    public static final String LIKE_PREFIX = "postLike:";

    /**
     * 帖子被点赞的次数(zSet)
     */
    public static final String LIKE_COUNT = "likeCount";
}
