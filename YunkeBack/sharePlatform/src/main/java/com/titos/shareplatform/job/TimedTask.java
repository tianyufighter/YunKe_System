package com.titos.shareplatform.job;

import com.titos.info.redis.constant.RedisPrefixConst;
import com.titos.info.shareplatform.entity.Post;
import com.titos.shareplatform.dao.PostDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName TimedTask
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 17:29
 **/
@Component
@Slf4j
public class TimedTask {

    @Resource
    private PostDao postDao;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Async
    @Scheduled(cron = "0 0 0/3 * * ?", zone = "Asia/Shanghai")
    public void saveLikes2DB() {
        log.info("========================================");
        log.info("开始同步redis的点赞数据到mysql！");
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(
                RedisPrefixConst.LIKE_COUNT, 0, -1);
        if (typedTuples != null) {
            for (ZSetOperations.TypedTuple<Object> sub : typedTuples) {
                log.info("同步帖子ID：{}，点赞量为：{}", sub.getValue(), Objects.requireNonNull(sub.getScore()).intValue());
                postDao.updateById(Post.builder()
                        .id((Integer) sub.getValue())
                        .likes(Objects.requireNonNull(sub.getScore()).intValue())
                        .build());
            }
        }
        log.info("同步redis的点赞数据到mysql完成！");
        log.info("========================================");
    }


}
