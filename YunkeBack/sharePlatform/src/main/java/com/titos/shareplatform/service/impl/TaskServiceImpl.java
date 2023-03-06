package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.shareplatform.entity.*;
import com.titos.info.shareplatform.enums.TaskAttributes;
import com.titos.info.shareplatform.vo.IdListVO;
import com.titos.info.shareplatform.vo.TaskVO;
import com.titos.shareplatform.dao.TagDao;
import com.titos.shareplatform.dao.TaskDao;
import com.titos.shareplatform.dao.TaskTagDao;
import com.titos.shareplatform.service.TagService;
import com.titos.shareplatform.service.TaskService;
import com.titos.shareplatform.service.TaskTagService;
import com.titos.tool.BeanCopyUtils.BeanCopyUtils;
import com.titos.tool.sensitive.SensitiveFilter;
import com.titos.tool.token.CustomStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName TaskServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/12 23:02
 **/
@Service
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskDao, Task> implements TaskService {

    @Resource
    private TaskDao taskDao;

    @Lazy
    @Resource
    private TaskService taskService;

    @Resource
    private TagDao tagDao;

    @Resource
    private TagService tagService;

    @Resource
    private TaskTagDao taskTagDao;

    @Resource
    private TaskTagService taskTagService;

    @Resource
    private SensitiveFilter sensitiveFilter;

    @Override
    public CommonResult<List<TaskVO>> listTask(CustomStatement customStatement, Long pageNum, Long pageSize) {
        Page<Task> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<Task>()
                .select(Task::getId, Task::getTaskTitle, Task::getTaskDesc,
                        Task::getIsImportant, Task::getIsStarred, Task::getIsDone, Task::getIsTrashed)
                .eq(Task::getUserId, customStatement.getId())
                .orderByDesc(Task::getCreateTime);
//        Page<Task> taskPage = taskDao.selectPage(page, queryWrapper);
        List<Task> taskList = taskDao.selectList(queryWrapper);
        List<TaskVO> taskVoList = BeanCopyUtils.copyList(taskList, TaskVO.class);
        taskVoList.forEach(item -> {
            item.setTagNameList(tagDao.listTagNameByTaskId(item.getId()));
        });
        return CommonResult.success(taskVoList);
    }

    @Override
    public CommonResult<List<TaskVO>> listTaskByTagName(CustomStatement customStatement, String tagName,
                                                        Long pageNum, Long pageSize) {
        List<TaskVO> taskVoList = taskDao.listTaskByTagName(customStatement.getId(), tagName);
        taskVoList.forEach(item -> {
            item.setTagNameList(tagDao.listTagNameByTaskId(item.getId()));
            item.setIsTrashed(false);
        });
        return CommonResult.success(taskVoList);
    }

    @Override
    public CommonResult<List<TaskVO>> listTaskByCondition(CustomStatement customStatement, String keywords,
                                                          Long pageNum, Long pageSize) {
        Page<Task> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<Task>()
                .select(Task::getId, Task::getTaskTitle, Task::getTaskDesc,
                        Task::getIsImportant, Task::getIsStarred, Task::getIsDone, Task::getIsTrashed)
                .eq(keywords.equals(TaskAttributes.IMPORTANT.getAttributes()), Task::getIsImportant, 1)
                .eq(keywords.equals(TaskAttributes.STARRED.getAttributes()), Task::getIsStarred, 1)
                .eq(keywords.equals(TaskAttributes.DONE.getAttributes()), Task::getIsDone, 1)
                .eq(keywords.equals(TaskAttributes.TRASHED.getAttributes()), Task::getIsTrashed, 1)
                .eq(Task::getUserId, customStatement.getId())
                .orderByDesc(Task::getCreateTime);
        List<Task> taskList = taskDao.selectList(queryWrapper);
        List<TaskVO> taskVoList = BeanCopyUtils.copyList(taskList, TaskVO.class);
        taskVoList.forEach(item -> {
            item.setTagNameList(tagDao.listTagNameByTaskId(item.getId()));
        });
        return CommonResult.success(taskVoList);
    }

    @Async
    @Override
    public void addOrUpdateTask(CustomStatement customStatement, TaskVO taskVO) {
        // 过滤敏感词
        taskVO.setTaskDesc(sensitiveFilter.filter(taskVO.getTaskDesc()));
        taskVO.setTaskTitle(sensitiveFilter.filter(taskVO.getTaskTitle()));
        Task task = BeanCopyUtils.copyObject(taskVO, Task.class);
        task.setUserId(customStatement.getId());
        taskService.saveOrUpdate(task);
        // 保存任务的tag
        saveTaskTag(taskVO, task.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult<Boolean> deleteTask(CustomStatement customStatement, IdListVO idListVO) {
        List<Integer> curTaskIdList = taskDao.selectList(new LambdaQueryWrapper<Task>()
                .select(Task::getId)
                .eq(Task::getUserId, customStatement.getId())).stream().map(Task::getId).collect(Collectors.toList());
        for (Integer taskId : idListVO.getIdList()) {
            if (!curTaskIdList.contains(taskId)) {
                return CommonResult.fail(StatusEnum.FAIL_DEL.getCode(), StatusEnum.FAIL_DEL.getMsg());
            }
        }
//        List<Integer> curTaskTagIdList = taskTagDao.selectList(new LambdaQueryWrapper<TaskTag>()
//                .select(TaskTag::getId)
//                .in(TaskTag::getTaskId, idListVO.getIdList())).stream().map(TaskTag::getId).collect(Collectors.toList());
//        taskTagDao.deleteBatchIds(curTaskTagIdList);
        taskDao.deleteBatchIds(idListVO.getIdList());
        return CommonResult.success(Boolean.TRUE);
    }

    private void saveTaskTag(TaskVO taskVO, Integer taskId) {
        // 更新任务则删除任务的所有标签
        if (Objects.nonNull(taskVO.getId())) {
            taskTagDao.delete(new LambdaQueryWrapper<TaskTag>()
                    .eq(TaskTag::getTaskId, taskVO.getId()));
        }
        List<String> tagNameList = taskVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagService.list(new LambdaQueryWrapper<Tag>()
                    .in(Tag::getTagName, tagNameList));
            List<String> existTagNameList = existTagList.stream().map(Tag::getTagName).collect(Collectors.toList());
            List<Integer> existTagIdList = existTagList.stream().map(Tag::getId).collect(Collectors.toList());

            // 从当前任务中移除已有的标签
            tagNameList.removeAll(existTagNameList);

            // 若该任务还有剩余标签，则添加到数据库中
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(item -> Tag.builder()
                                .tagName(item)
                                .build())
                        .collect(Collectors.toList());
                tagService.saveBatch(tagList);
                // 新加入的关于该任务的标签ID写入
                List<Integer> tagIdList = tagList.stream().map(Tag::getId).collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id，并绑定任务，再存入数据库
            List<TaskTag> taskTagList = existTagIdList.stream().map(item -> TaskTag.builder()
                            .taskId(taskId)
                            .tagId(item)
                            .build())
                    .collect(Collectors.toList());
            taskTagService.saveBatch(taskTagList);
        }
    }


}
