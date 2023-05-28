package com.titos.admin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.titos.admin.convert.ConfigConvert;
import com.titos.admin.dao.ConfigDao;
import com.titos.admin.model.Config;
import com.titos.admin.service.ConfigService;
import com.titos.admin.vo.ConfigVO;
import com.titos.info.global.CommonResult;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置 信息操作处理
 */
@RestController
@RequestMapping("/admin/config")
public class ConfigController {
    @Resource
    private ConfigService configService;
    @Resource
    private ConfigDao configDao;
    /**
     * 获取是否开启验证码功能
     */
    @GetMapping("/isCaptchaEnabled")
    public CommonResult getCaptchaEnabled() {
        Boolean res = configService.selectCaptchaEnabled();
        return CommonResult.success(res);
    }

    /**
     * 获取是否开启邮箱验证功能
     */
    @GetMapping("/isEmailValidateEnabled")
    public CommonResult getEmailValidateEnabled() {
        Boolean res = configService.selectEmailValidateEnabled();
        return CommonResult.success(res);
    }
    /**
     * 获取用户登录黑名
     */
    @GetMapping("/blockUsernameList")
    public CommonResult getBlockUsernameList() {
        String res = configService.selectBlockUsernameList();
        return CommonResult.success(res);
    }


    /**
     * 获取参数配置列表
     * @param configVO
     * @return
     */
    @GetMapping("/list")
    public CommonResult<PageInfo<Config>> list(ConfigVO configVO) {
        PageHelper.startPage(configVO.getPageNum(), configVO.getPageSize());
        List<Config> list = configService.selectConfigList(ConfigConvert.toConfigByConfigVO(configVO));
        return CommonResult.success(new PageInfo<>(list));
    }

    /**
     * 根据参数id获取详细信息
     */
    @GetMapping("/detail")
    public CommonResult getInfo(Integer id) {
        return CommonResult.success(configService.selectConfigById(id));
    }

    /**
     * 根据参数键查询参数值
     * @param configKey
     * @return
     */
    @GetMapping("/configKey")
    public CommonResult getConfigKey(String configKey) {
        return CommonResult.success(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @InjectToken
    @PostMapping("/addConfig")
    public CommonResult addConfig(CustomStatement customStatement, @RequestBody Config config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return CommonResult.fail("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUserId(customStatement.getId());
        Integer res = configService.insertConfig(config);
        if (res != 1) {
            return CommonResult.fail();
        } else {
            return CommonResult.success();
        }
    }

    /**
     * 修改参数配置
     * @param config
     * @return
     */
    @PostMapping("/updateConfig")
    public CommonResult edit(@RequestBody Config config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return CommonResult.fail("修改参数'" + config.getConfigName() + "'失败, 参数键名已存在");
        }
        Integer res = configService.updateConfig(config);
        if (res != 1) {
            return CommonResult.fail();
        } else {
            return CommonResult.success();
        }
    }

    /**
     * 删除参数
     * @param id
     * @return
     */
    @GetMapping("/deleteConfig")
    public CommonResult deleteConfig(Integer id) {
        configService.deleteConfigById(id);
        return CommonResult.success();
    }

    /**
     * 刷新参数缓存
     * @return
     */
    @GetMapping("/refreshCache")
    public CommonResult refreshCache() {
        configService.resetConfigCache();
        return CommonResult.success();
    }
}
