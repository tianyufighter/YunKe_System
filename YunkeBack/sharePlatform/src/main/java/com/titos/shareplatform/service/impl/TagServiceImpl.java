package com.titos.shareplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titos.info.shareplatform.entity.Tag;
import com.titos.shareplatform.dao.TagDao;
import com.titos.shareplatform.service.TagService;
import org.springframework.stereotype.Service;

/**
 * @ClassName TagServiceImpl
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 12:33
 **/
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
}
