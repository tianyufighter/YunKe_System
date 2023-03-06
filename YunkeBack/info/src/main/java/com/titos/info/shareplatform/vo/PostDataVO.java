package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PostDataVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/19 17:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDataVO {

    /**
     * 最近31天帖子每天被赞的总数
     */
    private Integer postLikesTotal;

    /**
     * 最近31天帖子每天被赞的次数列表
     */
    private List<Integer> postLikes;

    /**
     * 最近31天每天发表帖子的总数
     */
    private Integer postPublishedTotal;

    /**
     * 最近31天每天发表帖子的次数列表
     */
    private List<Integer> postPublished;

}
