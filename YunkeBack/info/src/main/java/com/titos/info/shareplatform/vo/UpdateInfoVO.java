package com.titos.info.shareplatform.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName UpdateInfoVO
 * @Description TODO
 * @Author Kurihada
 * @Date 2022/4/9 16:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateInfoVO {

    /**
     * 消息Id
     */
    private List<Integer> idList;

    /**
     * 消息状态
     */
    private Integer infoStatus;

}
