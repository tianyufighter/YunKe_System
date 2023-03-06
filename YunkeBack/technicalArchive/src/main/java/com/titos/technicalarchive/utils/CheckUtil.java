package com.titos.technicalarchive.utils;

import com.titos.technicalarchive.vo.BlogNumVO;
import com.titos.technicalarchive.vo.DetailBlogVO;
import com.titos.technicalarchive.vo.SimpleBlogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ddgo
 * @DateTime: 2022/4/12 10:47
 * @Version: 1.0.0
 * @Description:
 */
public class CheckUtil {
    private CheckUtil(){}
    public static List<SimpleBlogVO> defaultErrorBlogVOList = new ArrayList<>();
    public static DetailBlogVO defaultErrorDetailBlogVO = new DetailBlogVO();
    public static List<String> defaultErrorStringList = new ArrayList<>();
    public static BlogNumVO defaultErrorBlogNumVO = new BlogNumVO();
}
