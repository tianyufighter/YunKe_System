package com.titos.normal.controller;

import com.titos.info.file.FileInfo;
import com.titos.info.global.CommonResult;
import com.titos.info.global.enums.StatusEnum;
import com.titos.normal.factory.FileServiceFactory;
import com.titos.normal.service.file.FileService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Titos
 */
@RestController
@RefreshScope
@RequestMapping("/common/file")
public class FileController {
    @Value("${file.method}")
    private Integer fileMethod;
    @Resource
    private FileServiceFactory fileServiceFactory;

    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    @Headers("Content-Type: multipart/form-data")
    @PostMapping("/upload")
    public CommonResult<FileInfo> upload(@RequestParam MultipartFile file) {
        FileService fileService = fileServiceFactory.getFileService(fileMethod);
        FileInfo fileInfo = null;
        try {
            fileInfo = fileService.upload(file);
            return CommonResult.success(fileInfo, "文件上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<>(StatusEnum.FILE_SAVE_ERROR.getCode(), StatusEnum.FILE_SAVE_ERROR.getMsg());
        }
    }

    @GetMapping("/download")
    public CommonResult testFile(@RequestParam("filePath") String filePath, HttpServletResponse response) {
        FileService fileService = fileServiceFactory.getFileService(fileMethod);
        boolean isSuccess = fileService.download(filePath, response);
        if (isSuccess) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }

    @DeleteMapping("/delete")
    public CommonResult delete(String filePath) {
        FileService fileService = fileServiceFactory.getFileService(fileMethod);
        boolean isSuccess = fileService.delete(filePath);
        if (isSuccess) {
            return CommonResult.success();
        } else {
            return CommonResult.fail();
        }
    }
}
