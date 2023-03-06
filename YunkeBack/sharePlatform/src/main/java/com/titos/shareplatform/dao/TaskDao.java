package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.entity.Task;
import com.titos.info.shareplatform.vo.TaskVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TaskDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 23:04
 **/
@Repository
public interface TaskDao extends BaseMapper<Task> {
    /**
     * 根据分类名查询当前用户的任务
     *
     * @param userId   用户ID
     * @param tagName  分类名
     * @return 任务列表
     */
    List<TaskVO> listTaskByTagName(Integer userId, String tagName);
}
