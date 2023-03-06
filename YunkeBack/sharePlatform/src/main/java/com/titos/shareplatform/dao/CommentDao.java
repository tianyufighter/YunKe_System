package com.titos.shareplatform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titos.info.shareplatform.dto.CommentDTO;
import com.titos.info.shareplatform.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CommentDao
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/3 10:49
 **/
@Repository
public interface CommentDao extends BaseMapper<Comment> {
    /**
     * 根据帖子ID查询评论用户的信息
     *
     * @param postId 帖子Id
     * @return
     */
    List<CommentDTO> listUserByPostId(Integer postId);
}
