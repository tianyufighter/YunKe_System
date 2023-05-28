package com.titos.rpc.fallback;

import com.github.pagehelper.PageInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.news.vo.NewsConditionVO;
import com.titos.info.news.vo.NewsVO;
import com.titos.info.post.model.Post;
import com.titos.info.post.vo.IdListVO;
import com.titos.info.post.vo.PostNumVO;
import com.titos.info.post.vo.PostVO;
import com.titos.rpc.clients.SharePlatformServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharePlatformServiceClientFallbackFactory implements FallbackFactory<SharePlatformServiceClient> {
    @Override
    public SharePlatformServiceClient create(Throwable throwable) {
        return new SharePlatformServiceClient() {
            @Override
            public CommonResult<PageInfo<PostVO>> queryPostByCondition(PostNumVO postNumVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult updatePost(Post post) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult deletePostBatch(IdListVO idListVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult<PageInfo<NewsVO>> queryNewsByCondition(NewsConditionVO newsConditionVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult deleteNewsBatch(IdListVO idListVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult updateNews(NewsVO newsVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult addNews(NewsVO newsVO) {
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
