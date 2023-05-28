package com.titos.technicalarchive.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 更新博客公开状态的封装类
 * @author Titos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogStatusVO {
    /**
     * 博客的id列表
     */
    private List<Integer> idList;
    /**
     * 博客公开状态
     */
    private Integer blogStatus;
}
