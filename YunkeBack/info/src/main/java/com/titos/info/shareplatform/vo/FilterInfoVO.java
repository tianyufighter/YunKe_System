package com.titos.info.shareplatform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName FilterInfoVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 11:51
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterInfoVO {

    /**
     * 起始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 截至时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 信息类型
     */
    private Integer type;

    /**
     * 是否为最早发布
     */
    private Boolean isEarliest;
    /**
     * 匹配标题
     */
    private String matchTitle;

}
