package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.entity.Post;
import com.titos.info.shareplatform.vo.PostVO;
import com.titos.info.shareplatform.vo.TalentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName SharePlatformDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/3/31 23:10
 **/
@Repository
public interface PostDao extends BaseMapper<Post> {
    
    /**
     * 查询全部的帖子
     *
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @return 帖子集合
     */
    List<PostVO> listPost(Long pageNum, Long pageSize);

    /**
     * 查询活跃达人的ID
     *
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @return 活跃达人的Id集合
     */
    List<TalentVO> listTalentUserId(Long pageNum, Long pageSize);

    /**
     * 帖子减少评论数
     *
     * @param commentId 评论ID
     */
    void subComments(Integer commentId);
}
