package com.titos.normal.dao;

import com.titos.info.log.model.LoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统登录日志情况信息 数据层
 * @author Titos
 */
@Mapper
public interface LoginLogDao {
    /**
     * 新增系统登录日志
     * @param loginLog 访问日志对象
     */
    void insertLoginLog(LoginLog loginLog);

    /**
     * 查询系统登录日志集合
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    List<LoginLog> selectLoginLogList(LoginLog loginLog);

    /**
     * 批量删除系统登录日志
     * @param loginLogIds 需要删除的登录日志ID
     * @return 结果
     */
    Integer deleteLoginLogByIds(Integer[] loginLogIds);

    /**
     * 清空系统登录日志
     * @return 结果
     */
    Integer cleanLoginLog();
}
