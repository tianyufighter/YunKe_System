package com.titos.normal.controller;

import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.normal.service.cache.InfoCache;
import com.titos.tool.check.VerifyStringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/common/info")
public class InfoCacheController {

    @Resource
    private InfoCache infoCache;

    /**
     * 缓存信息
     * @param cacheInfoVO 缓存信息
     * @return 缓存信息对应的key
     */
    @PostMapping("/cacheInfo")
    public CommonResult cacheInfo(@RequestBody CacheInfoVO cacheInfoVO) {
        String key = infoCache.cacheInfo(cacheInfoVO);
        if (key != null) {
            return CommonResult.success(key);
        } else {
            return CommonResult.fail();
        }
    }

    /**
     * 获取信息
     * @param key 信息所对应的key
     * @return 信息
     */
    @PostMapping("/getInfo")
    public CommonResult getInfoByKey(@RequestBody String key) {
        if (VerifyStringUtil.isStringNull(key)) {
            return CommonResult.fail();
        }
        Object res = infoCache.getInfoByKey(key);
        if (res != null) {
            return new CommonResult<>(StatusEnum.SUCCESS.getCode(), res, "查询成功");
        } else {
            return CommonResult.fail();
        }
    }
}
