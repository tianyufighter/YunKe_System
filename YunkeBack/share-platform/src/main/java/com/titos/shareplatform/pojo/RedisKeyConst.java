package com.titos.shareplatform.pojo;


/**
 * redis中存储数据的键
 */
public class RedisKeyConst {

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
