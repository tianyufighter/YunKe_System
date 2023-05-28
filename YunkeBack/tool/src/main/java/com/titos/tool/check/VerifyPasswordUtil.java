package com.titos.tool.check;

import java.util.regex.Pattern;

/**
 * 校验密码是否符合要求
 * @author Titos
 */
public class VerifyPasswordUtil {
    /**
     * 匹配大写字母
     */
    private static final Pattern HAS_UPPER_CASE = Pattern.compile("^(?=.*?[A-Z]).{8,15}$");
    /**
     * 匹配数字
     */
    private static final Pattern HAS_NUM_CHAR = Pattern.compile("^(?=.*?[0-9]).{8,15}$");
    /**
     * 匹配小写字母
     */
    private static final Pattern HAS_LOWER_CASE = Pattern.compile("^(?=.*?[a-z]).{8,15}$");
    /**
     * 匹配特殊字符
     */
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("^(?=.*?[#?!@$%^&*-.]).{8,15}$");

    /**
     * 对密码进行校验。
     * 满足条件：
     * (1)长度必须在8~15之间
     * (2)密码中含有小写字母
     * (3)密码中含有数字
     * (4)密码中含有大写字母
     * (5)密码中含有特殊字符
     * 其中必须满足条件1, 且符合（2）、（3）、（4）、（5）中任意三个条件即可
     * @param pwd
     * @return
     */
    public static Boolean isPwdStrong(String pwd) {
        int count = 0;
        if (HAS_UPPER_CASE.matcher(pwd).matches()) {
            count++;
        }
        if (HAS_NUM_CHAR.matcher(pwd).matches()) {
            count++;
        }
        if (HAS_LOWER_CASE.matcher(pwd).matches()) {
            count++;
        }
        if (HAS_SPECIAL_CHAR.matcher(pwd).matches()) {
            count++;
        }
        return count > 2;
    }
}
