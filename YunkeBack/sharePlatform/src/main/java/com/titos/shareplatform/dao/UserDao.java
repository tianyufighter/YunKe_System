package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.user.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/11 23:05
 **/
@Repository
public interface UserDao extends BaseMapper<User> {
}
