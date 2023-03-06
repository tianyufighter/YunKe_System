package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName MyInfoVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 15:33
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyInfoVO {

    /**
     * 信息Id
     */
    private Integer id;

    /**
     * 信息标题
     */
    private String infoTitle;

    /**
     * 信息的类型
     * 1二手商品 2兼职信息 3失物招领
     */
    private Integer type;

    /**
     * 信息的状态
     * 1发布中 2已完成 3取消
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
