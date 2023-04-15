package com.titos.admin.service;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.post.model.Post;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;

import java.util.List;

/**
 * 帖子管理对应的服务层
 */
public interface PostService {
    /**
     * 分页查询所有的帖子信息
     * @param postNumVO 包含分页信息的帖子数据
     * @return 查询的帖子数据
     */
    PageInfo<Post> queryPostByPage(PostNumVO postNumVO);

    /**
     * 更新帖子信息
     * @param post
     * @return
     */
    Integer updatePost(Post post);

    /**
     * 批量删除帖子信息
     * @param idListVO
     * @return
     */
    CommonResult deletePostBatch(IdListVO idListVO);
}
