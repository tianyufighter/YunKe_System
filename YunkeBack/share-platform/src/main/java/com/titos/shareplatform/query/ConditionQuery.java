package com.titos.shareplatform.query;

import com.titos.shareplatform.vo.ConditionVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 查询新闻时的接收的参数
 * 根据新闻标题以及所给的时间范围来查询新闻
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConditionQuery {
    private List<Integer> newsIdList;
    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 截至时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
