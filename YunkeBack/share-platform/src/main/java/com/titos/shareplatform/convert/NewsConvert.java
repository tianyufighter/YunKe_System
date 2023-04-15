package com.titos.shareplatform.convert;

import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsVO;

public class NewsConvert {
    public static News toNewsByNewsVO(NewsVO newsVO) {
        return News.builder()
                .id(newsVO.getId())
                .newsCover(newsVO.getNewsCover())
                .newsTitle(newsVO.getNewsTitle())
                .newsContent(newsVO.getNewsContent())
                .userId(newsVO.getUserId())
                .createTime(newsVO.getCreateTime())
                .build();
    }
}
