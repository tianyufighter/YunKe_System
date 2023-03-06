package com.titos.personalmanagement.convert;

import com.titos.info.user.entity.UserInfoDomain;
import com.titos.personalmanagement.model.User;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nullable;

/**
 * @author Titos
 */
public class UserConvert {
    public static User toModel(@Nullable UserInfoDomain source) {
        User user = new User();
        BeanUtils.copyProperties(source, user);
        return user;
    }

    public static UserInfoDomain toDomain(@Nullable User source) {
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        BeanUtils.copyProperties(source, userInfoDomain);
        return userInfoDomain;
    }
}
