package com.titos.conversation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/3 20:51
 * @Version: 1.0.0
 * @Description:
 */
@Data
public class SimpleInformationVO {
    private String headImage;
    private String userName;
    private String content;
    private String friendId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;
    private Integer notReadNum;
}
