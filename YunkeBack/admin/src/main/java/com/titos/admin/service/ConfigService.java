package com.titos.admin.service;

import com.titos.admin.model.Config;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置 服务层
 */
public interface ConfigService {
    /**
     * 查询参数配置信息
     * @param id 参数配置id
     * @return 参数配置信息
     */
    Config selectConfigById(Integer id);

    /**
     * 根据键名查询参数配置信息
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectConfigByKey(String configKey);

    /**
     * 获取验证码开关
     * @return true 开启, false 关闭
     */
    Boolean selectCaptchaEnabled();

    /**
     * 获取邮箱验证开关
     * @return
     */
    Boolean selectEmailValidateEnabled();

    /**
     * 获取用户登录黑名单
     * @return
     */
    String selectBlockUsernameList();

    /**
     * 查询参数配置列表
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<Config> selectConfigList(Config config);

    /**
     * 新增参数配置
     * @param config 参数配置信息
     * @return 结果
     */
    Integer insertConfig(Config config);

    /**
     * 修改参数配置
     * @param config 参数配置信息
     * @return 结果
     */
    Integer updateConfig(Config config);

    /**
     * 删除参数信息
     * @param id 需要删除的参数ID
     */
    void deleteConfigById(Integer id);

    /**
     * 加载参数缓存数据
     */
    void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    void resetConfigCache();

    /**
     * 校参数名是否唯一
     * @param config 参数信息
     * @return 结果
     */
    Boolean checkConfigKeyUnique(Config config);
}
