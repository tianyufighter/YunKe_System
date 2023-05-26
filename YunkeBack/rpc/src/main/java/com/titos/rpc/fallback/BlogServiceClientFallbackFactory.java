package com.titos.rpc.fallback;

import com.github.pagehelper.PageInfo;
import com.titos.info.blog.model.Blog;
import com.titos.info.blog.vo.BlogVO;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.post.vo.IdListVO;
import com.titos.rpc.clients.BlogServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlogServiceClientFallbackFactory implements FallbackFactory<BlogServiceClient> {
    @Override
    public BlogServiceClient create(Throwable throwable) {
        return new BlogServiceClient() {
            @Override
            public CommonResult<PageInfo<BlogVO>> getBlogByCondition(BlogVO blogVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult updateBlog(Blog blog) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult deleteBlogBatch(IdListVO idListVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }
            /**
             * 通用的处理方法
             * @return
             */
            private CommonResult generalProcess() {
                return new CommonResult(StatusEnum.MICROSERVICE_ERROR.getCode(), StatusEnum.MICROSERVICE_ERROR.getMsg());
            }
        };
    }
}
