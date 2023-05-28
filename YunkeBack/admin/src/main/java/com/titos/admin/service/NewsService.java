package com.titos.admin.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.vo.IdListVO;

/**
 * 新闻管理 service层
 * @author Titos
 */
public interface NewsService {
    /**
     * 根据条件分页查询新闻信息
     * @param newsConditionVO
     * @return
     */
    PageInfo<NewsVO> queryNewsByPage(NewsConditionVO newsConditionVO);

    /**
     * 更新新闻信息
     * @param newsVO
     * @return
     */
    CommonResult updateNews(NewsVO newsVO);

    /**
     * 批量删除新闻信息
     * @param idListVO
     * @return
     */
    CommonResult deleteNews(IdListVO idListVO);

    /**
     * 添加新闻信息
     * @param newsVO
     * @return
     */
    CommonResult addNews(NewsVO newsVO);
}
