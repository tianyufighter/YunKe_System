package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName IdListVO
 * @Description 删除时传递的ID列表VO
 * @Author Kurihada
 * @Date 2022/4/10 21:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdListVO {

    /**
     * ID列表
     */
    private List<Integer> idList;

}
