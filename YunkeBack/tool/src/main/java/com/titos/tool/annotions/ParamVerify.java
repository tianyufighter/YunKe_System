package com.titos.tool.annotions;

import java.lang.annotation.*;

/**
 * @author Jirath
 * @date 2020/10/4
 * @description:
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamVerify {
    String[] notNull() default {};

    String[] sizeLimit() default {};

    String[] numberLimit() default {};

    boolean strictCollection() default true;
}