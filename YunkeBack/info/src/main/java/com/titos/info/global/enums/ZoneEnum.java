package com.titos.info.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ZoneEnum
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/7 18:36
 **/
@Getter
@AllArgsConstructor
public enum ZoneEnum {

    /**
     * 上海
     */
    SHANGHAI("Asia/Shanghai", "中国上海");


    /**
     * 时区
     */
    private final String zone;

    /**
     * 描述
     */
    private final String description;

}
