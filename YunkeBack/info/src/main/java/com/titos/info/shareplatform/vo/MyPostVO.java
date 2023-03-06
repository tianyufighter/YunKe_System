package com.titos.info.shareplatform.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName MyPostVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/8 20:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPostVO {

    /**
     * 帖子ID
     */
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
     * 点赞量
     */
    private Integer likes;

    /**
     * 帖子发表时间
     */
    private LocalDateTime createTime;

}
