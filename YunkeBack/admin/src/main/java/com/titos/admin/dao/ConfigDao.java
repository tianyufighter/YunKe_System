package com.titos.admin.dao;

import com.titos.admin.model.Config;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 参数配置 数据层
 * @author Titos
 */
@Mapper
public interface ConfigDao {
    /**
     * 查询参数配置信息
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    Config selectConfig(Config config);

    /**
     * 查询参数配置列表
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<Config> selectConfigList(Config config);
    /**
     * 根据ID查询配置
     * @param id 参数ID
     * @return 参数配置信息
     */
    Config selectConfigById(Integer id);

    /**
     * 根据键名查询参数配置信息
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    Config checkConfigKeyUnique(String configKey);

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
     * 删除参数配置
     * @param id 参数id
     * @return 结果
     */
    Integer deleteConfigById(Integer id);
}
