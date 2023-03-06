package com.titos.info.shareplatform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @ClassName AddCommentVO
 * @Description 增加评论VO
 * @Author Kurihada
 * @Date 2022/4/10 11:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentVO {

    /**
     * 评论文章ID
     */
    private Integer postId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String commentContent;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
