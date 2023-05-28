package com.titos.rpc.clients;

import com.github.pagehelper.PageInfo;
import com.titos.info.cache.vo.CacheInfoVO;
import com.titos.info.file.FileInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.log.model.LoginLog;
import com.titos.info.log.vo.LoginLogVO;
import com.titos.rpc.fallback.NormalServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@FeignClient(value = "normal", fallbackFactory = NormalServiceClientFallbackFactory.class)
public interface NormalServiceClient {
    @PostMapping("/common/info/cacheInfo")
    CommonResult cacheInfo(@RequestBody CacheInfoVO cacheInfoVO);

    @PostMapping("/common/info/getInfo")
    CommonResult getInfoByKey(@RequestBody String key);

    @PostMapping( "/common/file/upload")
    CommonResult<FileInfo> upload(@RequestParam MultipartFile file);

    @GetMapping( "/common/file/download")
    CommonResult testFile(@RequestParam("filePath") String filePath, HttpServletResponse response);

    @DeleteMapping("/common/file/delete")
    CommonResult delete(String filePath);

    @PostMapping("/common/log/loginLog/list")
    CommonResult<PageInfo<LoginLog>> loginLogList(@RequestBody LoginLogVO loginLogVO);

    @PostMapping("/common/log/loginLog/deleteBatch")
    CommonResult loginLogRemove(@RequestBody Map<String, Object> requestMap);

    @GetMapping("/common/log/loginLog/clean")
    CommonResult loginLogClean();

    @PostMapping("/common/log/loginlog/addLoginLog")
    CommonResult addLoginLog(@RequestBody LoginLog loginLog);

    @GetMapping("/common/sensitive/refresh")
    CommonResult refresh();

    @GetMapping("/common/sensitive/replace")
    CommonResult replaceContent(@RequestParam("text")String text);
}
