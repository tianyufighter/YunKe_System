package com.titos.shareplatform.dao;

import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.shareplatform.query.ConditionQuery;
import com.titos.shareplatform.vo.NewsDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NewsDao {
    /**
     * 根据新闻id列表以及时间范围来查询符合条件的新闻
     * @param conditionQuery
     * @return
     */
    List<News> selectNewsByCondition(ConditionQuery conditionQuery);

    /**
     * 根据条件分页查询新闻信息(管理员)
     * @param newsConditionVO 封装的条件
     * @return 返回符合条件的新闻信息
     */
    List<News> selectNewsByNewsConditionVO(NewsConditionVO newsConditionVO);

    /**
     * 根据id查询新闻信息
     * @param newsId 新闻id
     * @return
     */
    NewsDetailVO listNewsById(Integer newsId);

    /**
     * 模糊查询新闻
     * @param keywords
     * @return
     */
    List<News> selectNewsVague(String keywords);

    /**
     * 根据id批量删除新闻
     * @param ids
     * @return
     */
    Integer deleteNewsByIdBatch(List<Integer> ids);

    /**
     * 根据新闻id更新对应的新闻信息
     * @param news
     * @return
     */
    Integer updateNewsById(News news);

    /**
     * 添加新闻信息
     * @param news
     * @return
     */
    Integer addNews(News news);
}
