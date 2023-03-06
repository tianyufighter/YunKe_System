package com.titos.info.shareplatform.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName Info
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 0:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Info {

    /**
     * 信息Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 信息封面图
     */
    private String infoCover;

    /**
     * 信息标题
     */
    private String infoTitle;

    /**
     * 信息内容
     */
    private String infoContent;

    /**
     * 发表信息的用户Id
     */
    private Integer userId;

    /**
     * 信息的状态
     * 1发布中 2已完成 3取消
     */
    private Integer status;

    /**
     * 信息的类型
     * 1二手商品 2兼职信息 3失物招领
     */
    private Integer type;

    /**
     * 是否禁用
     */
    private Integer isViolation;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}