package com.titos.common.service.file.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileStream;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.titos.common.config.FastdfsConfig;
import com.titos.info.file.FileInfo;
import com.titos.info.global.enums.FileSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Titos
 */
@Service
public class FastdfsFileServiceImpl extends AbstractFileService {
    @Autowired
    private DefaultFastFileStorageClient defaultFastFileStorageClient;

    /**
     * 文件来源
     * @return
     */
    @Override
    protected FileSourceEnum fileSource() {
        return FileSourceEnum.FAST_DFS;
    }

    /**
     * 上传文件
     * @param file
     * @param fileInfo
     * @throws Exception
     */
    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        // 获取文件后缀名
        String sourceFileName = file.getOriginalFilename();
        // 获取后缀名
        String suffixName = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
        StorePath storePath = null;
        try {
            storePath = defaultFastFileStorageClient.uploadFile("group1", file.getInputStream(), fileInfo.getSize(), suffixName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileInfo.setUrl(storePath.getFullPath());
    }

    @Override
    protected boolean deleteFile(String filePath) {
        try {
            defaultFastFileStorageClient.deleteFile(FastdfsConfig.groupName, filePath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean downloadFile(String filePath, HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePath.substring(filePath.lastIndexOf("/") + 1), "UTF-8"));
            DownloadCallback<BufferedInputStream> callback = new DownloadFileStream(response.getOutputStream());
            defaultFastFileStorageClient.downloadFile(FastdfsConfig.groupName, filePath, callback);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
