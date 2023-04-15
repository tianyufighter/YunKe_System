package com.titos.info.post.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量删除帖子时接收的帖子id列表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IdListVO {
    /**
     * 帖子ID列表
     */
    private List<Integer> idList;
}
