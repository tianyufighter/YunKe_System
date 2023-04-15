package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.NewsTag;
import com.titos.shareplatform.model.NewsTagMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cglib.transform.impl.InterceptFieldTransformer;

import java.util.List;
@Mapper
public interface NewsTagMapDao {
    /**
     * 根据tagId查询所有的newsId
     * @param tagId
     * @return
     */
    List<Integer> selectNewsIdByTagId(Integer tagId);

    /**
     * 根据newsId获取TagId
     * @param newsId
     * @return
     */
    Integer selectTagIdByNewsId(Integer newsId);
    Integer deleteNewsTagMapById(List<Integer> newsIds);
    Integer deleteNewsTagMapByNewsTagMap(NewsTagMap newsTagMap);
    Integer insertNewsTagMap(NewsTagMap newsTagMap);
}
