package com.titos.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeVO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 通告内容
     */
    private String noticeContent;

}
