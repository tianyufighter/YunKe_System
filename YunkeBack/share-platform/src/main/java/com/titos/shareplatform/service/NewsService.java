package com.titos.shareplatform.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.vo.IdListVO;
import com.titos.shareplatform.vo.*;
import com.titos.tool.token.CustomStatement;

import java.util.List;

public interface NewsService {
    /**
     * 按条件查询新闻
     * @param conditionVO 查询条件
     * @param pageNum 当前页
     * @param pageSize 每页的数量
     * @return 新闻列表
     */
    CommonResult<PageInfo<NewsVO>> listNews(ConditionVO conditionVO, Integer pageNum, Integer pageSize);

    CommonResult<PageInfo<NewsVO>> queryNewsByNewsConditionVO(NewsConditionVO newsConditionVO);

    /**
     * 查询新闻详情
     * @param newsId 新闻主键ID
     * @return 新闻详情
     */
    CommonResult<NewsDetailVO> listNewsById(Integer newsId);

    /**
     * 搜索新闻
     * @param keywords 关键词
     * @return 新闻列表
     */
    CommonResult<List<NewsVO>> searchNews(String keywords);

    /**
     * 删除新闻
     * @param customStatement 用户信息
     * @param idListVO 新闻ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteNews(CustomStatement customStatement, IdListVO idListVO);

    /**
     * 查询所有的新闻的标签
     * @return 标签列表
     */
    CommonResult<List<NewsTagVO>> listNewsTag();

    /**
     * 批量删除新闻信息(管理员)
     * @param idListVO
     * @return
     */
    CommonResult deleteNewsBatch(IdListVO idListVO);

    /**
     * 更新新闻(管理员)
     * @param newsVO               封装的新的新闻信息
     * @return
     */
    CommonResult updateNews(NewsVO newsVO);

    /**
     * 添加新闻信息
     * @param newsVO
     * @return
     */
    CommonResult addNews(NewsVO newsVO);
}
