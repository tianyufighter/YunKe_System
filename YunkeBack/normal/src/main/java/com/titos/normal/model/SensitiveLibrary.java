package com.titos.normal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 敏感词库实体类
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SensitiveLibrary {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 敏感词
     */
    private String sensitiveWord;
}
