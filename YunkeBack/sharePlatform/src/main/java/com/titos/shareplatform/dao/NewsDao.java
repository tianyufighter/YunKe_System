package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.entity.News;
import com.titos.info.shareplatform.vo.NewsDetailVO;
import org.springframework.stereotype.Repository;

/**
 * @ClassName NewsDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 21:50
 **/
@Repository
public interface NewsDao extends BaseMapper<News> {
    /**
     * 查询新闻详情
     *
     * @param newsId 新闻主键ID
     * @return 新闻详情
     */
    NewsDetailVO listNewsById(Integer newsId);
}
