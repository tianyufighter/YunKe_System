package com.titos.shareplatform.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.titos.info.global.CommonResult;
import com.titos.rpc.clients.NormalServiceClient;
import com.titos.shareplatform.dao.TagDao;
import com.titos.shareplatform.dao.TaskDao;
import com.titos.shareplatform.dao.TaskTagDao;
import com.titos.shareplatform.model.Tag;
import com.titos.shareplatform.model.Task;
import com.titos.shareplatform.model.TaskTag;
import com.titos.shareplatform.service.TaskService;
import com.titos.info.post.vo.IdListVO;
import com.titos.shareplatform.vo.TaskVO;
import com.titos.tool.utils.BeanCopyUtils;
import com.titos.tool.token.CustomStatement;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskDao taskDao;
    @Resource
    private TagDao tagDao;
    @Resource
    private TaskTagDao taskTagDao;
    @Resource
    private NormalServiceClient normalServiceClient;
    @Override
    public CommonResult<List<TaskVO>> listTask(CustomStatement customStatement, Integer pageNum, Integer pageSize) {
        // 分页查询(紧随跟在其后的第一条查询sql将会被分页)
        PageHelper.startPage(pageNum, pageSize);
        List<Task> taskList = taskDao.selectAllTaskByUserIdDESC(customStatement.getId());
        List<TaskVO> taskVOList = BeanCopyUtils.copyList(taskList, TaskVO.class);
        taskVOList.forEach(item -> {
            List<Integer> tagIds = taskTagDao.selectTagIdByTaskId(item.getId());
            if (tagIds.size() != 0) {
                List<Tag> tags = tagDao.selectTagById(tagIds);
                List<String> tagNames = tags.stream().map(Tag::getTagName).collect(Collectors.toList());
                item.setTagNameList(tagNames);
            }
        });
        return CommonResult.success(taskVOList);
    }

    @Override
    public CommonResult<List<TaskVO>> listTaskByTagName(CustomStatement customStatement, String tagName, Integer pageNum, Integer pageSize) {
        // 根据用户id查询用户所有的任务
        List<Task> tasks = taskDao.selectAllTaskByUserIdDESC(customStatement.getId());
        // 根据标签名获取标签id
        Integer tagId = tagDao.selectIdByTagName(tagName);
        // 根据标签id查询该任务id
        List<Integer> taskIds = taskTagDao.selectTaskIdsByTagId(tagId);
        List<Task> myTask = new ArrayList<>();
        tasks.forEach(task -> {
            if (taskIds.contains(task.getId())) {
                myTask.add(task);
            }
        });
        List<TaskVO> taskVOList = BeanCopyUtils.copyList(myTask, TaskVO.class);
        taskVOList.forEach(item -> {
            List<Integer> tagIds = taskTagDao.selectTagIdByTaskId(item.getId());
            List<Tag> tags = tagDao.selectTagById(tagIds);
            List<String> tagNames = tags.stream().map(Tag::getTagName).collect(Collectors.toList());
            item.setTagNameList(tagNames);
            item.setIsTrashed(false);
        });
        return CommonResult.success(taskVOList);
    }

    @Override
    public CommonResult<List<TaskVO>> listTaskByCondition(CustomStatement customStatement, String keywords, Integer pageNum, Integer pageSize) {
        // 分页查询(紧随跟在其后的第一条查询sql将会被分页)
        PageHelper.startPage(pageNum, pageSize);
        List<Task> taskList = taskDao.selectAllTaskDynamic(keywords, customStatement.getId());
        List<TaskVO> taskVOList = BeanCopyUtils.copyList(taskList, TaskVO.class);
        taskVOList.forEach(item -> {
            List<Integer> tagIds = taskTagDao.selectTagIdByTaskId(item.getId());
            List<Tag> tags = tagDao.selectTagById(tagIds);
            List<String> tagNames = tags.stream().map(Tag::getTagName).collect(Collectors.toList());
            item.setTagNameList(tagNames);
        });
        return CommonResult.success(taskVOList);
    }

    @Async
    @Override
    public void addTask(CustomStatement customStatement, TaskVO taskVO) {
        // 过滤敏感词
        taskVO.setTaskDesc(Convert.convert(String.class, normalServiceClient.replaceContent(taskVO.getTaskDesc()).getData()));
        taskVO.setTaskTitle(Convert.convert(String.class, normalServiceClient.replaceContent(taskVO.getTaskTitle()).getData()));
        Task task = BeanCopyUtils.copyObject(taskVO, Task.class);
        task.setUserId(customStatement.getId());
        taskDao.insertTask(task);
        taskVO.setId(task.getId());
        saveTaskTag(taskVO);
    }

    @Override
    public void updateTask(CustomStatement customStatement, TaskVO taskVO) {
        // 过滤敏感词
        taskVO.setTaskDesc(Convert.convert(String.class, normalServiceClient.replaceContent(taskVO.getTaskDesc()).getData()));
        taskVO.setTaskTitle(Convert.convert(String.class, normalServiceClient.replaceContent(taskVO.getTaskTitle()).getData()));
        Task task = BeanCopyUtils.copyObject(taskVO, Task.class);
        task.setUserId(customStatement.getId());
        taskDao.updateTaskById(task);
        if (taskVO.getTagNameList() != null) {
            saveTaskTag(taskVO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteTask(CustomStatement customStatement, IdListVO idListVO) {
        for (Integer taskId : idListVO.getIdList()) {
            taskTagDao.deleteTaskTagByTaskId(taskId);
        }
        taskDao.deleteTaskBatch(idListVO.getIdList());
        return CommonResult.success(Boolean.TRUE);
    }

    /**
     * 保存任务所对应的标签
     * @param taskVO
     */
    private void saveTaskTag(TaskVO taskVO) {
        // 删除任务对应的所有标签
        if (Objects.nonNull(taskVO.getId())) {
            taskTagDao.deleteTaskTagByTaskId(taskVO.getId());
        }
        // 该任务对应的新的标签
        List<String> tagNameList = taskVO.getTagNameList();
        // 查询数据库中全部的标签
        List<Tag> tags = tagDao.selectTagByTagNames(tagNameList);
        tags.forEach(tag -> {
            TaskTag taskTag = new TaskTag();
            taskTag.setTaskId(taskVO.getId());
            taskTag.setTagId(tag.getId());
            taskTagDao.insertTaskTag(taskTag);
        });
    }
}
