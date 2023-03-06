package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LikesVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/10 14:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesVO {

    /**
     * 点赞帖子ID
     */
    private Integer postId;

}
