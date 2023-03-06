package com.titos.info.redis.constant;

/**
 * @ClassName RedisPrefixConst
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/5 10:51
 **/
public class RedisPrefixConst {

    /**
     * 活跃达人(zSet)
     */
    public static final String TALENT = "talent";

    /**
     * 点赞帖子的用户(set)
     */
    public static final String LIKE_KEY = "postLike:";

    /**
     * 帖子被点赞的次数(zSet)
     */
    public static final String LIKE_COUNT = "likeCount";

}
