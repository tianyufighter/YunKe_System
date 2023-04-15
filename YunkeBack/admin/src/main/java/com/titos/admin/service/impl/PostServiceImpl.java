package com.titos.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import com.titos.admin.service.PostService;
import com.titos.info.global.CommonResult;
import com.titos.info.post.model.Post;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;
import com.titos.rpc.clients.SharePlatformServiceClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private SharePlatformServiceClient sharePlatformServiceClient;

    @Override
    public PageInfo<Post> queryPostByPage(PostNumVO postNumVO) {
        PageInfo<Post> postPageInfo = Convert.convert(PageInfo.class, sharePlatformServiceClient.queryPostByCondition(postNumVO).getData());
        return postPageInfo;
    }

    @Override
    public Integer updatePost(Post post) {
        CommonResult commonResult = sharePlatformServiceClient.updatePost(post);
        if (commonResult.getCode() == 200 ) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public CommonResult deletePostBatch(IdListVO idListVO) {
        return sharePlatformServiceClient.deletePostBatch(idListVO);
    }
}
