package com.titos.common.service.file.impl;

import com.titos.common.service.file.FileService;
import com.titos.common.fileutil.FileUtil;
import com.titos.info.file.FileInfo;
import com.titos.info.global.enums.FileSourceEnum;
import com.titos.tool.exception.ParameterException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Titos
 */
public abstract class AbstractFileService implements FileService {
    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        if (!fileInfo.getName().contains(".")) {
            throw new ParameterException("缺少后缀名");
        }
        uploadFile(file, fileInfo);
        return fileInfo;
    }

    /**
     * 文件来源
     * @return
     */
    protected abstract FileSourceEnum fileSource();

    /**
     * 上传文件
     * @param file
     * @param fileInfo
     * @throws Exception
     */
    protected abstract void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception;

    @Override
    public boolean delete(String filePath) {
        return deleteFile(filePath);
    }

    /**
     * 删除文件资源
     * @param filePath
     * @return
     */
    protected abstract boolean deleteFile(String filePath);

    @Override
    public boolean download(String filePath, HttpServletResponse response) {
        return downloadFile(filePath, response);
    }

    /**
     * 下载文件
     */
    protected abstract boolean downloadFile(String filePath, HttpServletResponse response);
}
