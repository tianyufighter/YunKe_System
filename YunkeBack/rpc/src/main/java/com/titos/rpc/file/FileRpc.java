package com.titos.rpc.file;

import com.titos.info.file.FileInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.rpc.config.FeignConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传、下载、删除
 * @author Titos
 */
@FeignClient(value = "Common", configuration = FeignConfig.class)
public interface FileRpc {
    @PostMapping( "/upload")
    CommonResult<FileInfo> upload(@RequestParam MultipartFile file);

    @GetMapping( "/download")
    CommonResult testFile(@RequestParam("filePath") String filePath, HttpServletResponse response);

    @DeleteMapping("/delete")
    CommonResult delete(String filePath);
}
