package com.titos.admin.convert;

import com.titos.admin.model.Config;
import com.titos.admin.vo.ConfigVO;

/**
 * 将其他类转换成Config所对应的实体类
 */
public class ConfigConvert {
    public static Config toConfigByConfigVO(ConfigVO configVO) {
        return Config.builder()
                .id(configVO.getId())
                .configName(configVO.getConfigName())
                .configKey(configVO.getConfigKey())
                .configValue(configVO.getConfigValue())
                .configType(configVO.getConfigType())
                .userId(configVO.getUserId())
                .createTime(configVO.getCreateTime())
                .remark(configVO.getRemark())
                .build();
    }
}
