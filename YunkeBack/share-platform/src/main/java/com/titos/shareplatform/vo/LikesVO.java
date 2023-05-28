package com.titos.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesVO {
    /**
     * 点赞帖子id
     */
    private Integer postId;
}
