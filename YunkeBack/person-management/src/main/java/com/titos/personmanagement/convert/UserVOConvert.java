package com.titos.personmanagement.convert;

import com.titos.info.user.vo.UserVO;
import com.titos.info.personmanagement.vo.LoginVO;

public class UserVOConvert {
    public static UserVO toUserVOByLoginVO(LoginVO loginVO) {
        UserVO userVO = new UserVO();
        userVO.setUsername(loginVO.getUsername());
        userVO.setEmail(loginVO.getEmail());
        return userVO;
    }
}
