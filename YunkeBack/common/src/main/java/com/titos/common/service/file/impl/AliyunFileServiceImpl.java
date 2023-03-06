package com.titos.common.service.file.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.titos.common.config.AliyunConfig;
import com.titos.info.file.FileInfo;
import com.titos.info.global.enums.FileSourceEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Titos
 */
@Service
public class AliyunFileServiceImpl extends AbstractFileService {
    /**
     * 允许上传文件(图片)的格式
     */
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};
    @Resource
    private OSSClient ossClient;
    @Resource
    private AliyunConfig aliyunConfig;
    @Override
    protected FileSourceEnum fileSource() {
        return FileSourceEnum.ALIYUN;
    }

    /**
     * 上传文件
     * @param file 文件
     * @param fileInfo 文件信息
     */
    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) {
        try {
            getFilePath(fileInfo);
            // 校验图片格式
            boolean isLegal = false;
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(fileInfo.getSuffixName(), type)) {
                    isLegal = true;
                    break;
                }
            }
            // 如果图片格式不合法
            if (!isLegal) {
                throw new Exception("图片格式不合法");
            }
            /*
             * 下面两行代码是重点坑：
             * 现在阿里云OSS 默认图片上传ContentType是image/jpeg
             * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
             * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
             */
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");
            // 文件上传至阿里云OSS
            ossClient.putObject(aliyunConfig.getBucketName(), fileInfo.getPath(), file.getInputStream(), meta);
            ossClient.setBucketAcl(aliyunConfig.getBucketName(), CannedAccessControlList.PublicRead);
            /*
             * 注意：在实际项目中，文件上传成功后，数据库中存储文件地址
             */
            // 获取文件上传后的图片返回地址
            fileInfo.setUrl("https://" + aliyunConfig.getBucketName() + "." + aliyunConfig.getEndpoint() + "/" + fileInfo.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param filePath 文件在服务器上的存储路径，如filehost + "/" + filePath + "/" + fileName;
     * @return 删除的结果
     */
    @Override
    protected boolean deleteFile(String filePath) {
        try {
            ossClient.deleteObject(aliyunConfig.getBucketName(), filePath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 下载文件
     * @param filePath 文件在阿里云上的存储路径,如filehost + "/" + filePath + "/" + fileName;
     * @param response
     */
    @Override
    protected boolean downloadFile(String filePath, HttpServletResponse response) {
        // 文件名以附件的形式下载
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePath.substring(filePath.lastIndexOf("/") + 1), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //ossObject包含包含文件所在的存储空间、文件名称、文件元信息以及一个输入流
        OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), filePath);
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            // 读取文件内容
            InputStream inputStream = ossObject.getObjectContent();
            // 把输入流放入缓存流
            in = new BufferedInputStream(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            // 把输出流放入缓存流
            out = new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 生成路径以及文件名，
     * @param fileInfo 例如：images/2019/04/28/15564277465972939.jpg
     */
    private void getFilePath(FileInfo fileInfo) {
        // 新文件名称
        String newFileName = UUID.randomUUID().toString() + fileInfo.getSuffixName();
        fileInfo.setName(newFileName);
        // 构建日期路径，例如: OSS目标文件夹/2020/10/31/文件名
        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        // 文件上传的路径地址
        String uploadImageUrl = aliyunConfig.getFileHost() + "/" + filePath + "/" + newFileName;
        fileInfo.setPath(uploadImageUrl);
    }
}
