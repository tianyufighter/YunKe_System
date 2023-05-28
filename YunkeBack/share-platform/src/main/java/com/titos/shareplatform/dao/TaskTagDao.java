package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.TaskTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskTagDao {
    /**
     * 根据任务id查询标签id
     * @param taskId
     * @return
     */
    List<Integer> selectTagIdByTaskId(Integer taskId);
    List<Integer> selectTaskIdsByTagId(Integer tagId);

    /**
     * 根据taskId删除该任务所对应的标签
     * @param taskId
     * @return
     */
    Integer deleteTaskTagByTaskId(Integer taskId);

    /**
     * 插入任务的标签
     * @param taskTag
     * @return
     */
    Integer insertTaskTag(TaskTag taskTag);
}
