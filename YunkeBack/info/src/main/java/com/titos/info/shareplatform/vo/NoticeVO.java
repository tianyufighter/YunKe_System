package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName NoticeVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 17:59
 **/
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
