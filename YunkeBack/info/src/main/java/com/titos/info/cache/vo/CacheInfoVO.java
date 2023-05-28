package com.titos.info.cache.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * Controller层接收缓存信息的类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CacheInfoVO {
    /**
     * 缓存的信息对象
     */
    private Object obj;
    /**
     * 设置缓存的时长
     */
    private Long timeout;

    /**
     * 设置时间的类型，如TimeUnit.MINUTES
     */
    private TimeUnit timeUnit;

    public CacheInfoVO(Object obj, Long timeout, TimeUnit timeUnit) {
        this.obj = obj;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    /**
     * 存储时键的前缀, 默认为空串
     */
    private String prefixKey = "";
}
