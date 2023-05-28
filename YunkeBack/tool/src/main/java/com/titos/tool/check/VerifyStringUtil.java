package com.titos.tool.check;

/**
 * 验证字符串是否为空或长度为0
 * @author Titos
 */
public class VerifyStringUtil {
    public static boolean isStringNull(String s) {
        if (s==null || s.trim().length()==0){
            return true;
        }else {
            return false;
        }
    }
}
