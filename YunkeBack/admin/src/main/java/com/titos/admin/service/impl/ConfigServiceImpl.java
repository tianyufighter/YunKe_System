package com.titos.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.titos.admin.dao.ConfigDao;
import com.titos.admin.model.Config;
import com.titos.admin.service.ConfigService;
import com.titos.info.global.constant.CacheConstants;
import com.titos.tool.cache.redis.RedisCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 参数配置 服务层实现
 * @author Titos
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private ConfigDao configDao;

    @Resource
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询参数配置信息
     * @param id 参数配置id
     * @return 参数配置信息
     */
    @Override
    public Config selectConfigById(Integer id) {
        Config config = new Config();
        config.setId(id);
        return configDao.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     * @param configKey 参数键名
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        // 从缓存中读取该配置信息
        String configValue =redisCache.getCacheObject(getCacheKey(configKey));
        if (StringUtils.isNotEmpty(configValue)) { // 如果在缓存中查询到，则直接返回，反之则从数据库中查找
            return configValue;
        }
        Config config = new Config();
        config.setConfigKey(configKey);
        // 从数据库中查询该配置信息
        Config retConfig = configDao.selectConfig(config);
        if (retConfig != null) {
            // 将该配置信息添加到缓存中
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取验证码开关
     * @return true开启， false关闭
     */
    @Override
    public Boolean selectCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
        if (StringUtils.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    /**
     * 获取邮箱验证开关
     * @return true开启， false关闭
     */
    @Override
    public Boolean selectEmailValidateEnabled() {
        String emailValidateEnabled = selectConfigByKey("sys.account.emailValidate");
        if (StringUtils.isEmpty(emailValidateEnabled)) {
            return true;
        }
        return Convert.toBool(emailValidateEnabled);
    }

    /**
     * 获取用户登录的黑名单
     * @return
     */
    @Override
    public String selectBlockUsernameList() {
        String blockUsernameList = selectConfigByKey("sys.login.blockUsernameList");
        return blockUsernameList;
    }

    /**
     * 查询参数配置列表
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<Config> selectConfigList(Config config) {
        return configDao.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public Integer insertConfig(Config config) {
        int row = configDao.insertConfig(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public Integer updateConfig(Config config) {
        Config temp = configDao.selectConfigById(config.getId());
        // 判断需要更新的参数的键是否相等
        if (!StringUtils.equals(temp.getConfigKey(), config.getConfigKey())) {
            redisCache.deleteObject(getCacheKey(temp.getConfigKey()));
        }
        int row = configDao.updateConfig(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 根据id删除对应的参数信息
     * @param id 需要删除的参数ID
     */
    @Override
    public void deleteConfigById(Integer id) {
        Config config = selectConfigById(id);
        // 查询该参数是否为内置参数，如果是内置参数,则不能删除
        if (StringUtils.equals("Y", config.getConfigType())) {
            throw new RuntimeException(String.format("内置参数【%1$s】不能删除", config.getConfigKey()));
        }
        configDao.deleteConfigById(id);
        redisCache.deleteObject(getCacheKey(config.getConfigKey()));
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        List<Config> configList = configDao.selectConfigList(new Config());
        for (Config config : configList) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();;
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public Boolean checkConfigKeyUnique(Config config) {
        Integer configId = config.getId() == null ? -1 : config.getId();
        Config info = configDao.checkConfigKeyUnique(config.getConfigKey());
        if (info != null && info.getId() != configId) {
            return false;
        }
        return true;
    }

    /**
     * 设置cache key
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.SYS_CONFIG_KEY + configKey;
    }
}
