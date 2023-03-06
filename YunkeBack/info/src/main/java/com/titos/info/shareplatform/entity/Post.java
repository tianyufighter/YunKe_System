package com.titos.info.shareplatform.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName Post
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/3/30 22:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    /**
     * 帖子ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子封面图片
     */
    private String postCover;

    /**
     * 发布帖子的用户ID
     */
    private Integer userId;

    /**
     * 评论量
     */
    private Integer comments;

    /**
     * 点赞量
     */
    private Integer likes;

    /**
     * 帖子是否违规，默认否
     */
    private Boolean isViolation;

    /**
     * 帖子发表时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
