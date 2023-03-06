package com.titos.common.service.file;

import com.titos.info.file.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Titos
 */
public interface FileService {
    /**
     * 上传文件
     * @param file 需要上传的文件
     * @return 上传的文件信息
     * @throws Exception 抛出的异常
     */
    FileInfo upload(MultipartFile file) throws Exception;

    /**
     * 下载文件
     * @param filePath 文件的根路径（除去URL部分）
     * @return 删除的结果
     */
    boolean delete(String filePath);

    /**
     * 下载文件
     * @param filePath 文件的根路径 (除去URL部分)
     * @param response
     * @return 下载的结果
     */
    boolean download(String filePath, HttpServletResponse response);
}
