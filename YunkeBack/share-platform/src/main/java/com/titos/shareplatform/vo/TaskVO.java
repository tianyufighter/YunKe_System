package com.titos.shareplatform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskVO {

    /**
     * 任务ID
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 任务的标签名
     */
    private List<String> tagNameList;

}
