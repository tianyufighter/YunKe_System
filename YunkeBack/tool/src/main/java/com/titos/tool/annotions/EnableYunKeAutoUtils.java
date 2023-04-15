package com.titos.tool.annotions;

import com.titos.tool.conf.YunKeToolConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Titos
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({YunKeToolConfiguration.class})
public @interface EnableYunKeAutoUtils {
}
