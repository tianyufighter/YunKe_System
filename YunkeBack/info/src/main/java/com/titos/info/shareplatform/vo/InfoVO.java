package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName InfoVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 0:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoVO {

    /**
     * 信息Id
     */
    private Integer id;

    /**
     * 信息封面图
     */
    private String infoCover;

    /**
     * 信息标题
     */
    private String infoTitle;

    /**
     * 信息内容
     */
    private String infoContent;

    /**
     * 发表信息的用户Id
     */
    private Integer userId;

    /**
     * 发表信息的用户名
     */
    private String username;

    /**
     * 发表信息的用户的联系方式
     */
    private String email;

    /**
     * 信息的类型
     * 1二手商品 2兼职信息 3失物招领
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
