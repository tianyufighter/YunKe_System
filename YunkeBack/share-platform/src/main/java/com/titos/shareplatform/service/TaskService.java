package com.titos.shareplatform.service;

import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;
import com.titos.shareplatform.vo.TaskVO;
import com.titos.tool.token.CustomStatement;

import java.util.List;

public interface TaskService {
    /**
     * 分页查询当前用户所有的任务
     * @param customStatement 用户信息
     * @param pageNum 当前页
     * @param pageSize 每页的数量
     * @return 任务列表
     */
    CommonResult<List<TaskVO>> listTask(CustomStatement customStatement, Integer pageNum, Integer pageSize);

    /**
     * 根据分类名分页查询当前用户的任务
     * @param customStatement
     * @param tagName
     * @param pageNum
     * @param pageSize
     * @return
     */
    CommonResult<List<TaskVO>> listTaskByTagName(CustomStatement customStatement, String tagName, Integer pageNum, Integer pageSize);
    /**
     * 按属性分页查询当前用户的任务
     *
     * @param customStatement 用户信息
     * @param keywords        查询类别
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    CommonResult<List<TaskVO>> listTaskByCondition(CustomStatement customStatement, String keywords, Integer pageNum, Integer pageSize);

    /**
     * 添加修改任务
     *
     * @param customStatement 用户信息
     * @param taskVO          任务
     */
    void addTask(CustomStatement customStatement, TaskVO taskVO);

    /**
     * 修改任务
     *
     * @param customStatement 用户信息
     * @param taskVO          任务
     */
    void updateTask(CustomStatement customStatement, TaskVO taskVO);

    /**
     * 批量删除任务
     * @param customStatement 用户信息
     * @param idListVO 任务ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteTask(CustomStatement customStatement, IdListVO idListVO);
}
