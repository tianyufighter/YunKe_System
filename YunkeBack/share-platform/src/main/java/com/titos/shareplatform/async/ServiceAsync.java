package com.titos.shareplatform.async;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.titos.info.global.constant.CacheConstants;
import com.titos.info.user.model.User;
import com.titos.rpc.clients.UserServiceClient;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ServiceAsync {
    @Resource
    private UserServiceClient userServiceClient;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 异步更新帖子排行榜中，用户帖子的数量
     * @param customStatement
     */
    @Async
    public void subActiveScore(CustomStatement customStatement) {
        User user = Convert.convert(User.class, userServiceClient.queryUserById(customStatement.getId()).getData());
        log.info(JSON.toJSONString(user));
        Double score = redisTemplate.opsForZSet().incrementScore(CacheConstants.ACTIVE, JSON.toJSONString(user), -1.0D);
        if (score != null && score == 0) {
            redisTemplate.opsForZSet().remove(CacheConstants.ACTIVE, JSON.toJSONString(user));
        }
    }
}
