package com.titos.tool.annotions;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamVerify {
    String[] notNull() default {};

    String[] sizeLimit() default {};

    String[] numberLimit() default {};

    boolean strictCollection() default true;
}