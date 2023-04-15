package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.NewsTag;
import com.titos.shareplatform.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NewsTagDao {
    /**
     * 根据id查询tag名
     * @param id
     * @return
     */
    String selectTagNameById(Integer id);

    /**
     * 根据标签名，查询标签信息
     * @param tagName
     * @return
     */
    NewsTag selectTagByTagName(String tagName);

    /**
     * 添加新的标签
     * @param newsTag
     * @return
     */
    Integer insertTag(NewsTag newsTag);

    /**
     * 查询所有的新闻标签
     * @return
     */
    List<NewsTag> selectAllNewsTag();
}
