package com.titos.shareplatform.service;


import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.post.model.Post;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;
import com.titos.shareplatform.vo.*;
import com.titos.tool.token.CustomStatement;

import java.util.List;

public interface PostService {
    /**
     * 查询所有的帖子信息
     * @return
     */
    List<PostVO> queryAllPosts();

    /**
     * 分页查询所有的帖子
     * @param customStatement 用户信息
     * @param pageNum 当前页
     * @param pageSize 每页的数量
     * @return
     */
    CommonResult<PageInfo<PostVO>> queryPostsByPage(CustomStatement customStatement, int pageNum, int pageSize);

    /**
     * 获取当前用户的帖子
     * @param customStatement 用户信息
     * @return 帖子列表
     */
    CommonResult<List<Post>> listMyPost(CustomStatement customStatement);

    /**
     * 查询活跃达人
     * @param pageNum 当前页
     * @param pageSize 每页的数量
     * @return 结果
     */
    CommonResult<List<ActiveVO>> listActive(Long pageNum, Long pageSize);

    /**
     * 新增帖子
     * @param customStatement 用户信息
     * @param addPostVO 帖子信息
     */
    void addPost(CustomStatement customStatement, AddPostVO addPostVO);

    /**
     * 批量删除帖子
     * @param customStatement 用户信息
     * @param postIdListVO 帖子列表
     * @return 删除是否成功
     */
    CommonResult<Boolean> deletePosts(CustomStatement customStatement, IdListVO postIdListVO);

    /**
     * 点赞帖子
     * @param customStatement 点赞的用户信息
     * @param likesVO 被点赞的帖子信息
     */
    void savePostLike(CustomStatement customStatement, LikesVO likesVO);

    /**
     * 获取当前用户最近31天帖子每天被赞的次数列表和每天发表帖子的次数列表
     * @param customStatement 用户信息
     * @return 最近31天帖子每天被赞的次数列表和每天发表帖子的次数列表
     */
    CommonResult<PostDataVO> getPostLike(CustomStatement customStatement);

    /**
     * 按条件查询帖子信息(管理员)
     * @param postNumVO 封装的帖子信息
     * @return 查询的结果
     */
    CommonResult<PageInfo<Post>> queryPostByCondition(PostNumVO postNumVO);

    /**
     * 更新帖子信息
     * @param post
     * @return
     */
    Integer updatePost(Post post);

    /**
     * 批量删除帖子信息(管理员)
     * @param idListVO 需要删除的Id列表
     * @return
     */
    Integer deletePostBatch(IdListVO idListVO);
}
