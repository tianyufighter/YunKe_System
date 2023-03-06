package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.entity.NewsTag;
import org.springframework.stereotype.Repository;

/**
 * @ClassName NewsTagDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/18 19:33
 **/
@Repository
public interface NewsTagDao extends BaseMapper<NewsTag> {
    /**
     * 通过新闻ID查询标签名
     * @param newsId 新闻Id
     * @return 标签名
     */
    String listTagNameByNewsId(Integer newsId);
}
