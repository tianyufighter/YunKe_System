package com.titos.shareplatform.dao;

import com.titos.shareplatform.model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagDao {
    /**
     * 根据标签id来查询标签名
     * @param id 标签id
     * @return 标签名
     */
    String selectTagNameById(Integer id);

    /**
     * 根据标签名查询标签的id
     * @param tagName
     * @return
     */
    Integer selectIdByTagName(String tagName);

    /**
     * 查询属于该tagNames中所对应的Tag
     * @param tagNames
     * @return
     */
    List<Tag> selectTagByTagNames(List<String> tagNames);
    List<Tag> selectTagById(List<Integer> ids);
}
