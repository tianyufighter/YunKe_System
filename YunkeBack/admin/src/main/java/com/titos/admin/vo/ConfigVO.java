package com.titos.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分页查询列表时接收前端的实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConfigVO {
    /**
     * 需要查询的页面
     */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
    /**
     * 参数主键
     */
    private Integer id;
    /**
     * 参数名称
     */
    private String configName;
    /**
     * 参数键名
     */
    private String configKey;
    /**
     * 参数键值
     */
    private String configValue;
    /**
     * 系统内置(Y是 N否)
     */
    private String configType;
    /**
     * 创建者的id
     */
    private Integer userId;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 备注
     */
    private String remark;
}
