package com.titos.conversation.utils;

import com.titos.conversation.po.MessagePO;
import com.titos.conversation.vo.SimpleInformationVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/12 12:12
 * @Version: 1.0.0
 * @Description:
 */
public class CheckUtil {
    private CheckUtil(){}
    public static List<MessagePO> defaultErrorMessagePoList = new ArrayList<>();
    public static List<SimpleInformationVO> defaultErrorSimpleInformationVO = new ArrayList<>();
}
