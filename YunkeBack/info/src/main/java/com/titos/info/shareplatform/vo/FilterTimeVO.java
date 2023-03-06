package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName FilterTimeVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/13 22:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterTimeVO {

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

}
