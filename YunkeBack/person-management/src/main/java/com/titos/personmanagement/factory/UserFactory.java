package com.titos.personmanagement.factory;


import com.titos.info.user.model.User;

import java.util.Date;

/**
 * 根据修改后的user信息创建新的User
 * @author Titos
 */
public class UserFactory {
    public User build(User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setPersonType(1);
        newUser.setRegistryTime(new Date());
        return newUser;
    }
}
