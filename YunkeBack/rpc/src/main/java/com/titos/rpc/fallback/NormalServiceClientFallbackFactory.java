package com.titos.rpc.fallback;

import com.github.pagehelper.PageInfo;
import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.info.file.FileInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.info.log.model.LoginLog;
import com.titos.info.log.vo.LoginLogVO;
import com.titos.rpc.clients.NormalServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class NormalServiceClientFallbackFactory implements FallbackFactory<NormalServiceClient> {
    @Override
    public NormalServiceClient create(Throwable throwable) {
        return new NormalServiceClient() {
            @Override
            public CommonResult cacheInfo(CacheInfoVO cacheInfoVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult getInfoByKey(String key) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult<FileInfo> upload(MultipartFile file) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult testFile(String filePath, HttpServletResponse response) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult delete(String filePath) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult<PageInfo<LoginLog>> loginLogList(LoginLogVO loginLogVO) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult loginLogRemove(Map<String, Object> requestMap) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult loginLogClean() {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult addLoginLog(LoginLog loginLog) {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult refresh() {
                log.error("sentinel-----微服务调用异常");
                return generalProcess();
            }

            @Override
            public CommonResult replaceContent(String text) {
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
