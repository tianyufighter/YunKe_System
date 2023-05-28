package com.titos.userservice.convert;

import com.titos.info.personmanagement.vo.LoginVO;
import com.titos.tool.utils.BeanCopyUtils;
import com.titos.userservice.dto.UserDTO;

public class UserDTOConvert {
    public static UserDTO toUserDTOByLoginVO(LoginVO loginVO) {
        return BeanCopyUtils.copyObject(loginVO, UserDTO.class);
    }
}
