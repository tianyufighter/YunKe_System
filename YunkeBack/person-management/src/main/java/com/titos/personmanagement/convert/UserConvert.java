package com.titos.personmanagement.convert;

import com.titos.info.user.model.User;
import com.titos.personmanagement.vo.RegisterVO;
import com.titos.tool.utils.BeanCopyUtils;
import org.springframework.lang.Nullable;

public class UserConvert {
    public static User toUserByRegisterVO(@Nullable RegisterVO registerVO) {
        User user = BeanCopyUtils.copyObject(registerVO, User.class);
        return user;
    }
}
