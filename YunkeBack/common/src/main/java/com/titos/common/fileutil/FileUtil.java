package com.titos.common.fileutil;

import com.titos.info.file.FileInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;

/**
 * @author Titos
 */
public class FileUtil {
    public static FileInfo getFileInfo(MultipartFile file) throws Exception {
        String md5 = fileMd5(file.getInputStream());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(md5);
        // 获取文件原名称
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());
        fileInfo.setIsImg(fileInfo.getContentType().startsWith("image/")? 1L : 0L);
        fileInfo.setSize(file.getSize());
        fileInfo.setCreateTime(LocalDateTime.now());
        fileInfo.setSuffixName(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        return fileInfo;
    }

    /**
     * 文件的md5
     * @param inputStream
     * @return
     */
    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
