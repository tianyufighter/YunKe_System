package com.titos.tool.annotions;

import java.lang.annotation.*;

/**
 * @author Titos
 * @date 2020/11/17
 * @description:
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InjectToken {
}
