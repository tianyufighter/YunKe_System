package com.titos.shareplatform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 通告内容
     */
    private String noticeContent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 状态，1表示公开，0表示不公开
     */
    private Integer status;
    /**
     * 用户id
     */
    private Integer userId;
}