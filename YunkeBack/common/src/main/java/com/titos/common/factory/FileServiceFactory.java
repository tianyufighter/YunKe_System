package com.titos.common.factory;

import com.titos.common.service.file.FileService;
import com.titos.info.global.enums.FileSourceEnum;
import com.titos.tool.exception.ParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author Titos
 */
@Component
public class FileServiceFactory {
    private HashMap<FileSourceEnum, FileService> fileServiceMap = new HashMap<FileSourceEnum, FileService>();
    @Autowired
    private FileService aliyunFileServiceImpl;
    @Autowired
    private FileService fastdfsFileServiceImpl;
    @PostConstruct
    private void initFileService() {
        fileServiceMap.put(FileSourceEnum.ALIYUN, aliyunFileServiceImpl);
        fileServiceMap.put(FileSourceEnum.FAST_DFS, fastdfsFileServiceImpl);
    }

    public FileService getFileService(Integer fileSourceCode) throws ParameterException {
        if (fileSourceCode.equals(FileSourceEnum.FAST_DFS.getCode())) {
            return fileServiceMap.get(FileSourceEnum.FAST_DFS);
        } else if (fileSourceCode.equals(FileSourceEnum.ALIYUN.getCode())) {
            return fileServiceMap.get(FileSourceEnum.ALIYUN);
        }
        throw new ParameterException("文件标识码错误");
    }
}
