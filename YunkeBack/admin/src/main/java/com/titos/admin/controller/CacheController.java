package com.titos.admin.controller;

import com.titos.admin.model.SysCache;
import com.titos.admin.model.server.Sys;
import com.titos.info.global.CommonResult;
import com.titos.info.global.constant.CacheConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 缓存监控
 * @author Titos
 */
@RestController
@RequestMapping("/admin/cache")
public class CacheController {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private final static List<SysCache> caches = new ArrayList<>();
    {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "秘密错误次数"));
    }

    @GetMapping()
    public CommonResult getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());
        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);
        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return CommonResult.success(result);
    }
    @GetMapping("/getNames")
    public CommonResult cache() {
        return CommonResult.success(caches);
    }
    @GetMapping("/getKeys")
    public CommonResult getCacheKeys(@RequestParam("cacheName") String cacheName) {
        Set<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        return CommonResult.success(cacheKeys);
    }
    @GetMapping("/getValue")
    public CommonResult getCacheValue(@RequestParam("cacheName") String cacheName, @RequestParam("cacheKey") String cacheKey) {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return CommonResult.success(sysCache);
    }
    @GetMapping("/clearCacheName")
    public CommonResult clearCacheName(@RequestParam("cacheName") String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cache() + "*");
        redisTemplate.delete(cacheKeys);
        return CommonResult.success();
    }
    @GetMapping("/clearCacheKey")
    public CommonResult clearCacheKey(@RequestParam("cacheKey") String cacheKey) {
        redisTemplate.delete(cacheKey);
        return CommonResult.success();
    }
    @GetMapping("/clearCacheAll")
    public CommonResult clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        redisTemplate.delete(cacheKeys);
        return CommonResult.success();
    }
}
