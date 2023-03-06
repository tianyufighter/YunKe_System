package com.titos.personalmanagement.factory;

import com.titos.info.user.query.LoginQuery;
import com.titos.personalmanagement.model.User;

/**
 * LoginQuery对象构造器
 * @author Titos
 */
public class LoginQueryFactory {
    public LoginQuery build(User user) {
        return new LoginQuery(user.getEmail(), user.getPassword(), "");
    }
}
