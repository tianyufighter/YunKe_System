package com.titos.shareplatform.controller;

import com.titos.info.global.CommonResult;
import com.titos.shareplatform.service.TaskService;
import com.titos.info.post.vo.IdListVO;
import com.titos.shareplatform.vo.TaskVO;
import com.titos.tool.annotions.InjectToken;
import com.titos.tool.token.CustomStatement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sharePlatform/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    /**
     * 分页查询当前用户所有的任务
     *
     * @param customStatement 用户信息
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    @InjectToken
    @GetMapping("/list")
    public CommonResult<List<TaskVO>> listTask(
            CustomStatement customStatement,
            @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        return taskService.listTask(customStatement, pageNum, pageSize);
    }

    /**
     * 根据分类名分页查询当前用户的任务
     *
     * @param customStatement 用户信息
     * @param tagName         分类名
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    @InjectToken
    @GetMapping("/tags")
    public CommonResult<List<TaskVO>> listTaskByTagName(
            CustomStatement customStatement,
            String tagName,
            @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        return taskService.listTaskByTagName(customStatement, tagName, pageNum, pageSize);
    }

    /**
     * 按属性分页查询当前用户的任务
     *
     * @param customStatement 用户信息
     * @param keywords        查询类别
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 任务列表
     */
    @InjectToken
    @GetMapping("/condition")
    public CommonResult<List<TaskVO>> listTaskByCondition(
            CustomStatement customStatement,
            String keywords,
            @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        return taskService.listTaskByCondition(customStatement, keywords, pageNum, pageSize);
    }

    /**
     * 添加任务
     *
     * @param customStatement 用户信息
     * @param taskVO          任务
     * @return 是否成功
     */
    @InjectToken
    @PostMapping("/addOrUpdate")
    public CommonResult<Boolean> addOrUpdateTask(
            CustomStatement customStatement,
            @RequestBody TaskVO taskVO) {
        if (taskVO.getId() != null) {
            taskService.updateTask(customStatement, taskVO);
        } else {
            taskService.addTask(customStatement, taskVO);
        }
        return CommonResult.success(Boolean.TRUE);
    }

    /**
     * 批量删除任务
     * @param customStatement 用户信息
     * @param idListVO 任务ID列表
     * @return 是否删除成功
     */
    @InjectToken
    @DeleteMapping("/delete")
    public CommonResult<Boolean> deleteTask(
            CustomStatement customStatement,
            @RequestBody IdListVO idListVO) {
        return taskService.deleteTask(customStatement, idListVO);
    }
}
