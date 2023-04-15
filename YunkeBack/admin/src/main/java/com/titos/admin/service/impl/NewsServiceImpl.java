package com.titos.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import com.titos.admin.service.NewsService;
import com.titos.info.global.CommonResult;
import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.rpc.clients.SharePlatformServiceClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    private SharePlatformServiceClient sharePlatformServiceClient;
    @Override
    public PageInfo<NewsVO> queryNewsByPage(NewsConditionVO newsConditionVO) {
        PageInfo pageInfo = Convert.convert(PageInfo.class, sharePlatformServiceClient.queryNewsByCondition(newsConditionVO).getData());
        return pageInfo;
    }

    @Override
    public CommonResult updateNews(NewsVO newsVO) {
        return sharePlatformServiceClient.updateNews(newsVO);
    }

    @Override
    public CommonResult deleteNews(IdListVO idListVO) {
        return sharePlatformServiceClient.deleteNewsBatch(idListVO);
    }

    @Override
    public CommonResult addNews(NewsVO newsVO) {
        return sharePlatformServiceClient.addNews(newsVO);
    }
}
