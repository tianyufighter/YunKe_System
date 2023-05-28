package com.titos.admin.controller;

import com.github.pagehelper.PageInfo;
import com.titos.admin.service.PostService;
import com.titos.info.global.CommonResult;
import com.titos.info.post.model.Post;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/post")
public class PostController {
    @Resource
    private PostService postService;

    /**
     * 按条件查询分页查询帖子信息
     * @param postNumVO
     * @return
     */
    @PostMapping("/getPostByCondition")
    public CommonResult<PageInfo<Post>> queryPost(@RequestBody PostNumVO postNumVO) {
        return CommonResult.success(postService.queryPostByPage(postNumVO));
    }

    /**
     * 修改帖子信息
     * @param post
     * @return
     */
    @PostMapping("/updatePost")
    public CommonResult updatePost(@RequestBody Post post) {
        Integer res = postService.updatePost(post);
        if (res == 1) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    /**
     * 批量删除帖子信息
     * @param idListVO
     * @return
     */
    @PostMapping("/deletePost")
    public CommonResult deletePostBatch(@RequestBody IdListVO idListVO) {
        return postService.deletePostBatch(idListVO);
    }
}
