package com.titos.personmanagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回系统在线人数和总人数
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OnlineUserVO {
    /**
     * 在线人数
     */
    private Integer onlineUserCount;
    /**
     * 系统总人数
     */
    private Integer totalUserCount;
}