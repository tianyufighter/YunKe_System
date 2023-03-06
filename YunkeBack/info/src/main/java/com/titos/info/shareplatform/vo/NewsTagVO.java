package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName NewsTagVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/18 18:49
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
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
