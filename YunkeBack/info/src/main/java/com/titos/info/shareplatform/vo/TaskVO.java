package com.titos.info.shareplatform.vo;

import com.titos.info.shareplatform.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName TaskVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 20:33
 **/
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
     * 任务的标签名列表
     */
    private List<String> tagNameList;

}
