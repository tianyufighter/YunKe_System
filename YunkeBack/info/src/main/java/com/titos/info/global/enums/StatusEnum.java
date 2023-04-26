package com.titos.info.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 状态枚举类
 *
 * @author Titos
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnum {
    SENTINEL_FLOW_EXCEPTION(100, "流控异常"),
    SENTINEL_DEGRADE_EXCEPTION(101, "降级异常"),
    SENTINEL_PARAMFLOW_EXCEPTION(102, "热点异常"),
    SENTINEL_SYSTEM_EXCEPTION(103, "系统异常"),
    SENTINEL_AUTHORITY_EXCEPTION(104, "授权异常"),
    SENTINEL_UNKNOWN_EXCEPTION(105, "未知异常"),
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),
    /**
     * 没有登录
     */
    UN_LOGIN(401, "没有登录"),
    /**
     * token错误
     */
    TOKEN_ERROR(402, "token错误"),
    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),
    /**
     * 密码太弱
     */
    PASSWORD_IS_NOT_STRONG(404, "密码太弱"),
    /**
     * 用户名已经存在
     */
    USERNAME_EXISTED(405, "用户名已经存在"),
    /**
     * 邮箱已经存在
     */
    EMAIL_EXISTED(405, "邮箱已经存在"),
    /**
     * 邮箱错误
     */
    MAIL_ERROR(406, "邮箱错误"),
    /**
     * 密码错误
     */
    PASSWORD_WRONG(407, "密码错误"),
    /**
     * 文件大小错误
     */
    FILE_SIZE_ERROR(408, "文件大小错误"),
    /**
     * 文件保存错误
     */
    FILE_SAVE_ERROR(409, "文件保存错误"),
    /**
     * 验证错误
     */
    VERIFY_ERROR(410, "验证错误"),
    /**
     * 用户不存在
     */
    USER_UNEXISTED(411, "用户不存在"),
    /**
     * Token过期
     */
    TOKEN_EXPIRED(412, "token过期"),
    /**
     * token为空
     */
    TOKEN_NULL(413, "token为空"),
    /**
     * Token未知异常
     */
    TOKEN_UNKNOWN(414, "token未知异常"),
    /**
     * 服务器错误
     */
    ERROR(500, "服务器错误"),
    /**
     * 权限不足
     */
    AUTHORITY_LACK(501, "权限不足"),
    /**
     * 账户禁用
     */
    ACCOUNT_DISABLE(502, "账户禁用"),
    /**
     * 微服务调用出错
     */
    MICROSERVICE_ERROR(503, "微服务调用错误"),
    /**
     * 失败
     */
    FAIL(51000, "操作失败"),

    /**
     * 删除权限不足
     */
    FAIL_DEL(51001, "删除权限不足");
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 根据状态码返回枚举成员
     *
     * @param code 状态码
     * @return 枚举成员
     */
    public static StatusEnum valueOf(int code) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("argument out of range");
    }
}
