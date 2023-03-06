package com.titos.info.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileInfo {
    private String id;
    private String name;
    private String contentType;
    private Long isImg;
    private Long size;
    private LocalDateTime createTime;
    /**
     * 文件后缀名
     */
    private String suffixName;
    private String path;
    private String url;
}
