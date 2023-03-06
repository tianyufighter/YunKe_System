package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.global.PageResult;
import com.titos.info.shareplatform.entity.News;
import com.titos.info.shareplatform.vo.*;
import com.titos.tool.token.CustomStatement;

import java.util.List;

/**
 * @ClassName NewsService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 21:49
 **/
public interface NewsService extends IService<News> {
    /**
     * 按条件查询新闻
     *
     * @param conditionVO 查询条件
     * @param pageNum     当前页
     * @param pageSize    每页的数量
     * @return 新闻列表
     */
    CommonResult<PageResult<NewsVO>> listNews(ConditionVO conditionVO, Long pageNum, Long pageSize);

    /**
     * 查询新闻详情
     *
     * @param newsId 新闻主键ID
     * @return 新闻详情
     */
    CommonResult<NewsDetailVO> listNewsById(Integer newsId);

    /**
     * 搜索新闻
     *
     * @param keywords 关键词
     * @return 新闻列表
     */
    CommonResult<List<NewsVO>> searchNews(String keywords);

    /**
     * 删除新闻
     *
     * @param customStatement 用户信息
     * @param idListVO        新闻ID列表
     * @return 是否删除成功
     */
    CommonResult<Boolean> deleteNews(CustomStatement customStatement, IdListVO idListVO);

    /**
     * 查询所有的新闻的标签
     * @return 标签列表
     */
    CommonResult<List<NewsTagVO>> listNewsTag();
}
