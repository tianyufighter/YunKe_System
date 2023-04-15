package com.titos.rpc.clients;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.post.vo.IdListVO;
import com.titos.rpc.fallback.BlogServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "technicalArchive", fallbackFactory = BlogServiceClientFallbackFactory.class)
public interface BlogServiceClient {
    @PostMapping("/technicalArchive/blog/getBlogByCondition")
    CommonResult<PageInfo<Blog>> getBlogByCondition(@RequestBody BlogVO blogVO);
    @PostMapping("/technicalArchive/blog/updateBlog")
    CommonResult updateBlog(@RequestBody Blog blog);

    @PostMapping("/technicalArchive/blog/deleteBlogBatch")
    CommonResult deleteBlogBatch(@RequestBody IdListVO idListVO);
}
