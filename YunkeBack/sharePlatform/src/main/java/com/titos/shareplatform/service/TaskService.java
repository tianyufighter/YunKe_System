package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Task;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.info.shareplatform.vo.TaskVO;
import com.titos.tool.token.CustomStatement;

import java.util.List;

/**
 * @ClassName TaskService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 23:02
 **/
public interface TaskService extends IService<Task> {
    /**
     * 分页查询当前用户所有的任务
     *
     * @param customStatement 用户信息
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    CommonResult<List<TaskVO>> listTask(CustomStatement customStatement, Long pageNum, Long pageSize);

    /**
     * 根据分类名分页查询当前用户的任务
     *
     * @param customStatement 用户信息
     * @param tagName         分类名
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    CommonResult<List<TaskVO>> listTaskByTagName(CustomStatement customStatement, String tagName,
                                                 Long pageNum, Long pageSize);


    /**
     * 按属性分页查询当前用户的任务
     *
     * @param customStatement 用户信息
     * @param keywords        查询类别
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    CommonResult<List<TaskVO>> listTaskByCondition(CustomStatement customStatement, String keywords, Long pageNum, Long pageSize);

    /**
     * 添加或者修改任务
     *
     * @param customStatement 用户信息
     * @param taskVO          任务
     */
    void addOrUpdateTask(CustomStatement customStatement, TaskVO taskVO);

    /**
     * 批量删除任务
     * @param customStatement 用户信息
     * @param idListVO 任务ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteTask(CustomStatement customStatement, IdListVO idListVO);
}
