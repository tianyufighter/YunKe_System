package com.titos.shareplatform.async;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.titos.info.redis.constant.RedisPrefixConst;
import com.titos.info.user.entity.User;
import com.titos.shareplatform.dao.UserDao;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName ServiceAsync
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/11 23:53
 **/
@Component
@Slf4j
public class ServiceAsync {

    @Resource
    private UserDao userDao;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Async
    public void subTalentScore(CustomStatement customStatement) {
        User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getHeadImage)
                .eq(User::getId, customStatement.getId()));
        log.info(JSON.toJSONString(user));
        Double score = redisTemplate.opsForZSet().incrementScore(RedisPrefixConst.TALENT, JSON.toJSONString(user), -1.0D);
        if (score != null && score == 0) {
            redisTemplate.opsForZSet().remove(RedisPrefixConst.TALENT, JSON.toJSONString(user));
        }
    }

}
