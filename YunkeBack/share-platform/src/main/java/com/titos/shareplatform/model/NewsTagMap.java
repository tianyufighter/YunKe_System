package com.titos.shareplatform.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName NewsTagMap
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/18 19:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsTagMap {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 新闻ID
     */
    private Integer newsId;

    /**
     * 标签ID
     */
    private Integer tagId;

}
