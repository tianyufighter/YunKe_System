package com.titos.info.global;

import com.titos.info.global.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回类(与前端或与其它系统进行交互时返回的统一参数格式)
 *
 * @author Titos
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommonResult<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 消息
     */
    private String message;

    public CommonResult(Integer code, T data) {
        this(code, data, null);
    }

    public CommonResult(Integer code, String message) {
        this(code, null, message);
    }

    /**
     * 什么都不带的成功
     *
     * @param <T> 数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>( StatusEnum.SUCCESS.getCode(),null, StatusEnum.SUCCESS.getMsg());
    }

    /**
     * 带数据的成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(StatusEnum.SUCCESS.getCode(), data, StatusEnum.SUCCESS.getMsg());
    }

    /**
     * 带数据和消息的成功
     *
     * @param data    数据
     * @param message 消息
     * @param <T>     数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(StatusEnum.SUCCESS.getCode(), data, message);
    }

    /**
     * 什么都不带的失败
     *
     * @param <T> 数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> fail() {
        return new CommonResult<>( StatusEnum.FAIL.getCode(),null, StatusEnum.FAIL.getMsg());
    }

    /**
     * 带状态码的失败
     *
     * @param statusEnum 状态码
     * @param <T>        数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> fail(StatusEnum statusEnum) {
        return new CommonResult<>(statusEnum.getCode(),null, statusEnum.getMsg());
    }

    /**
     * 带数据的失败
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> fail(T data) {
        return new CommonResult<>(StatusEnum.FAIL.getCode(), data, StatusEnum.FAIL.getMsg());
    }

    /**
     * 带数据和消息的失败
     *
     * @param data    数据
     * @param message 消息
     * @param <T>     数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> fail(T data, String message) {
        return new CommonResult<>(StatusEnum.FAIL.getCode(), data, message);
    }

    /**
     * 带状态码和消息的失败
     *
     * @param code    状态码
     * @param message 消息
     * @param <T>     数据类型
     * @return restResult
     */
    public static <T> CommonResult<T> fail(Integer code, String message) {
        return new CommonResult<>(code, null, message);
    }
}
