package com.titos.personalmanagement.convert;

import com.titos.info.user.query.RegisterQuery;
import com.titos.personalmanagement.model.User;

/**
 * @author Titos
 */
public class RegisterQueryConvert {
    public static User convert(RegisterQuery registerQuery) {
        User user = new User();
        user.setId(registerQuery.getId());
        user.setUsername(registerQuery.getUsername());
        user.setPassword(registerQuery.getPassword());
        user.setGender(registerQuery.getGender());
        user.setEmail(registerQuery.getEmail());
        user.setPersonType(registerQuery.getPersonType());
        user.setHeadImage(registerQuery.getHeadImage());
        user.setBirthday(registerQuery.getBirthday());
        user.setPhone(registerQuery.getPhone());
        user.setRegistryTime(registerQuery.getRegistryTime());
        return user;
    }
}
