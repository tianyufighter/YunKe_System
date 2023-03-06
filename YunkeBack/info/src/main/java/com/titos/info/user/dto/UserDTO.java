package com.titos.info.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserDTO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/2 23:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户头像
     */
    private String headImage;

}
