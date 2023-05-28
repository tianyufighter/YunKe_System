package com.titos.info.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Titos
 */

@Getter
@AllArgsConstructor
public enum FileSourceEnum {
    /**
     * 本地存储
     */
    LOCAL(1, "LOCAL"),
    /**
     * 阿里云OOS存储
     */
    ALIYUN(2, "ALIYUN"),
    /**
     * FASTDFS存储
     */
    FAST_DFS(3, "FAST_DFS");

    private final int code;
    private final String desc;
    /**
     * 根据文件表示码返回枚举成员
     *
     * @param code 文件标识码
     * @return 枚举成员
     */
    public static FileSourceEnum valueOf(int code) {
        for (FileSourceEnum value : FileSourceEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("argument out of range");
    }
}
