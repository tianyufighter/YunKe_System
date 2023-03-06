package com.titos.shareplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titos.info.global.CommonResult;
import com.titos.info.shareplatform.entity.Post;
import com.titos.info.shareplatform.vo.*;
import com.titos.info.shareplatform.vo.TalentVO;
import com.titos.tool.token.CustomStatement;

import java.util.List;

/**
 * @ClassName PostService
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/3/30 21:51
 **/
public interface PostService extends IService<Post> {

    /**
     * 分页查询所有的帖子
     *
     * @param customStatement 用户信息
     * @param pageNum         当前页
     * @param pageSize        每页的数量
     * @return 帖子列表
     */
    CommonResult<List<PostVO>> listPost(CustomStatement customStatement, Long pageNum, Long pageSize);

    /**
     * 获取当前用户的帖子
     *
     * @param customStatement 用户信息
     * @return 帖子列表
     */
    CommonResult<List<MyPostVO>> listMyPost(CustomStatement customStatement);
//    CommonResult<List<MyPostVO>> listMyPost(CustomStatement customStatement, Long pageNum, Long pageSize);

    /**
     * 查询活跃达人
     *
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @return 结果
     */
    CommonResult<List<TalentVO>> listTalent(Long pageNum, Long pageSize);

    /**
     * 新建帖子
     *
     * @param customStatement 用户信息
     * @param addPostVO       帖子信息
     */
    void addPost(CustomStatement customStatement, AddPostVO addPostVO);

    /**
     * 批量删除帖子
     *
     * @param customStatement 用户信息
     * @param idListVO        帖子列表
     * @return 删除是否成功
     */
    CommonResult<Boolean> deletePosts(CustomStatement customStatement, IdListVO idListVO);

    /**
     * 点赞帖子
     *
     * @param customStatement 点赞用户信息
     * @param likesVO         被点赞的帖子信息
     */
    void savePostLike(CustomStatement customStatement, LikesVO likesVO);

    /**
     * 获取当前用户最近31天帖子每天被赞的次数列表和每天发表帖子的次数列表
     * @param customStatement 用户信息
     * @return 最近31天帖子每天被赞的次数列表和每天发表帖子的次数列表
     */
    CommonResult<PostDataVO> getPostLike(CustomStatement customStatement);
}
