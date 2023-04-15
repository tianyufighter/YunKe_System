package com.titos.rpc.clients;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.news.model.News;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.model.Post;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;
import com.titos.rpc.fallback.SharePlatformServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "sharePlatform", fallbackFactory = SharePlatformServiceClientFallbackFactory.class)
public interface SharePlatformServiceClient {
    @PostMapping("/sharePlatform/post/queryPostByPage")
    CommonResult<PageInfo<Post>> queryPostByCondition(@RequestBody PostNumVO postNumVO);
    @PostMapping("/sharePlatform/post/updatePost")
    CommonResult updatePost(@RequestBody Post post);

    @PostMapping("/sharePlatform/post/deletePostBatch")
    CommonResult deletePostBatch(@RequestBody IdListVO idListVO);

    @PostMapping("/sharePlatform/news/getNewsByCondition")
    CommonResult<PageInfo<NewsVO>> queryNewsByCondition(@RequestBody NewsConditionVO newsConditionVO);

    @PostMapping("/sharePlatform/news/deleteNewsBatch")
    CommonResult deleteNewsBatch(@RequestBody IdListVO idListVO);

    @PostMapping("/sharePlatform/news/updateNews")
    CommonResult updateNews(@RequestBody NewsVO newsVO);

    @PostMapping("/sharePlatform/news/addNews")
    CommonResult addNews(@RequestBody NewsVO newsVO);
}
