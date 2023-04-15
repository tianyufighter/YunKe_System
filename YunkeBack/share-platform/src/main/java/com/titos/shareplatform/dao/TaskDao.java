package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.Task;
import com.titos.shareplatform.vo.TaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskDao {
    /**
     * 根据用户id查询该用户所有的任务信息(按照时间降序)
     * @param userId 用户id
     * @return
     */
    List<Task> selectAllTaskByUserIdDESC(Integer userId);

    /**
     * 添加任务
     * @param task
     * @return
     */
    Integer insertTask(Task task);

    /**
     * 根据任务id查询所有的任务信息(按照时间降序)
     * @param id 任务id
     * @return
     */
    List<Task> selectAllTaskByIdDESC(Integer id);

    /**
     * 根据keywords(isTrashed, IsImportant, IsStarred, IsDOne)来查询任务
     * @return
     */
    List<Task> selectAllTaskDynamic(@Param("keywords") String keywords, @Param("userId") Integer userId);

    /**
     * 根据任务id来动态修改任务
     * @param task 任务
     * @return
     */
    Integer updateTaskById(Task task);

    /**
     * 根据用户id查询taskId
     * @param userId
     * @return
     */
    List<Integer> selectIdByUserId(Integer userId);

    /**
     * 根据id批量删除任务
     * @param ids
     * @return
     */
    Integer deleteTaskBatch(List ids);
}
