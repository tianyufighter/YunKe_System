package com.titos.shareplatform.model;

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
 * @ClassName NewsTag
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/18 18:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsTag {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
