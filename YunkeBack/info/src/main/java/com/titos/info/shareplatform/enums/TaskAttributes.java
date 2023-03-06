package com.titos.info.shareplatform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName Attributes
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 10:35
 **/
@Getter
@AllArgsConstructor
public enum TaskAttributes {

    /**
     * 任务属性：收藏
     */
    STARRED("starred"),

    /**
     * 任务属性：重要
     */
    IMPORTANT("important"),

    /**
     * 任务属性：完成
     */
    DONE("done"),

    /**
     * 任务属性：删除
     */
    TRASHED("trashed");

    private final String attributes;

}
