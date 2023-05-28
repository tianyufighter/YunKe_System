package com.titos.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewsTagVO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 标签名
     */
    private String tagName;
}
