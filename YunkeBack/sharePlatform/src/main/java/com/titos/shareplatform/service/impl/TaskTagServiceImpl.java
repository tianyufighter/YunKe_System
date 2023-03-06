package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.shareplatform.entity.TaskTag;
import com.titos.shareplatform.dao.TaskTagDao;
import com.titos.shareplatform.service.TaskTagService;
import org.springframework.stereotype.Service;

/**
 * @ClassName TaskTagServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 12:52
 **/
@Service
public class TaskTagServiceImpl extends ServiceImpl<TaskTagDao, TaskTag> implements TaskTagService {
}
