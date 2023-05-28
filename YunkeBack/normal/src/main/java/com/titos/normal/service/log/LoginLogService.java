package com.titos.normal.service.log;

import com.titos.info.log.model.LoginLog;

import java.util.List;

/**
 * 系统登录日志情况信息 服务层
 * @author Titos
 */
public interface LoginLogService {
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
     * @param ids 需要删除的登录日志ID
     * @return 结果
     */
    Integer deleteLoginLogByIds(Integer[] ids);

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
