package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName AddInfoVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 15:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddInfoVO {

    /**
     * 信息ID
     */
    private Integer id;

    /**
     * 信息封面图
     */
    private String infoCover;

    /**
     * 信息标题
     */
    @NotBlank(message = "信息标题不能为空")
    private String infoTitle;

    /**
     * 信息内容
     */
    @NotBlank(message = "信息内容不能为空")
    private String infoContent;

    /**
     * 信息的类型
     * 1二手商品 2兼职信息 3失物招领
     */
    private Integer type;

}
