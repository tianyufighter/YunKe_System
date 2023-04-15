package com.titos.admin.convert;

import com.titos.admin.model.Notice;
import com.titos.admin.vo.NoticeVO;

/**
 * 将其他类转换成Notice所对应的实体类
 */
public class NoticeConvert {
    public static Notice toNoticeByNoticeVO(NoticeVO noticeVO) {
        return Notice.builder()
                .id(noticeVO.getId())
                .noticeContent(noticeVO.getNoticeContent())
                .createTime(noticeVO.getCreateTime())
                .userId(noticeVO.getUserId())
                .status(noticeVO.getStatus())
                .build();
    }
}
