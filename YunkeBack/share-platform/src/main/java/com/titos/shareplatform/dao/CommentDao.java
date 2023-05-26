package com.titos.shareplatform.dao;

import com.titos.info.comment.dto.CommentDTO;
import com.titos.shareplatform.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    /**
     * 插入一条评论信息
     * @param comment
     * @return
     */
    Integer insertComment(Comment comment);

    /**
     * 根据帖子id和用户id查询评论
     * @param userId
     * @param postId
     * @return
     */
    Comment selectCommentByPostIdAndUserId(@Param("userId") Integer userId, @Param("postId") Integer postId);

    /**
     * 根据评论的id，删除评论
     * @param ids
     * @return
     */
    Integer deleteCommentBatchById(Integer[] ids);

    /**
     * 根据帖子Id删除对应的评论信息
     * @param postId 帖子id
     * @return
     */
    Integer deleteCommentByPostId(@Param("postId") Integer postId);

    /**
     * 根据帖子的id查询该帖子的所有评论
     * @param postId 帖子id
     * @return
     */
    List<CommentDTO> selectCommentByPostId(Integer postId);

    /**
     * 根据用户id查询该用户所有的评论
     * @param userId 用户id
     * @return
     */
    List<Integer> selectCommentIdByUserId(Integer userId);

    /**
     * 根据id查询评论信息
     * @param id
     * @return
     */
    Comment selectCommentById(Integer id);
}
