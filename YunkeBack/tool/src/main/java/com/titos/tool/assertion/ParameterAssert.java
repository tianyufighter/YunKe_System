package com.titos.tool.assertion;

import com.titos.tool.exception.ParameterException;

/**
 * 参数断言
 * @author Titos
 */
public class ParameterAssert {
    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new ParameterException(msg);
        }
    }
}