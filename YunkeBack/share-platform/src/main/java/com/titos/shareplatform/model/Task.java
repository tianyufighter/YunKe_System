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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 发布任务的用户ID
     */
    private Integer userId;

    /**
     * 任务是否重要
     */
    private Boolean isImportant;

    /**
     * 任务是否收藏
     */
    private Boolean isStarred;

    /**
     * 任务是否已完成
     */
    private Boolean isDone;

    /**
     * 任务是否已删除
     */
    private Boolean isTrashed;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}