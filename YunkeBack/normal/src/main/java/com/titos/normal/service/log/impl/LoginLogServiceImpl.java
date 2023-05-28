package com.titos.normal.service.log.impl;

import com.titos.info.log.model.LoginLog;
import com.titos.normal.dao.LoginLogDao;
import com.titos.normal.service.log.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private LoginLogDao loginLogDao;

    /**
     * 新增系统登录日志
     * @param loginLog 访问日志对象
     */
    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogDao.insertLoginLog(loginLog);
    }

    /**
     * 查询系统登录日志集合
     * @param loginLog 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<LoginLog> selectLoginLogList(LoginLog loginLog) {
        return loginLogDao.selectLoginLogList(loginLog);
    }

    /**
     * 批量删除系统登录日志
     * @param ids 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public Integer deleteLoginLogByIds(Integer[] ids) {
        return loginLogDao.deleteLoginLogByIds(ids);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginLog() {
        loginLogDao.cleanLoginLog();
    }
}
