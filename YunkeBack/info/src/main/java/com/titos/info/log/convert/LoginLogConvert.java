package com.titos.info.log.convert;

import com.titos.info.log.model.LoginLog;
import com.titos.info.log.vo.LoginLogVO;

public class LoginLogConvert {
    public static LoginLog toLoginLogByLoginLogVO(LoginLogVO loginLogVO) {
        return LoginLog.builder()
                .id(loginLogVO.getId())
                .username(loginLogVO.getUsername())
                .browser(loginLogVO.getBrowser())
                .os(loginLogVO.getOs())
                .status(loginLogVO.getStatus())
                .msg(loginLogVO.getMsg())
                .loginTime(loginLogVO.getLoginTime())
                .build();
    }
}
