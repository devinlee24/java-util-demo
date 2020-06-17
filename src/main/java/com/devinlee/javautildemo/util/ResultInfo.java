package com.devinlee.javautildemo.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装统一返回结果
 *
 * @author devinlee
 */
@Data
public class ResultInfo<T> implements Serializable {

    // 状态码
    private Integer code;

    // 返回消息
    private String message;

    // 返回数据
    private T data;

    public ResultInfo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 统一返回
    private static<T> ResultInfo returnData(Integer code, String message, T data) {

        return new ResultInfo(code, message, data);
    }

    // SUCCESS
    public static ResultInfo success() {

        return returnData(Response.SUCCESS.code(), Response.SUCCESS.message(), null);
    }

    public static<T> ResultInfo success(T data) {

        return returnData(Response.SUCCESS.code(), Response.SUCCESS.message(), data);
    }

    public static<T> ResultInfo success(String message, T data) {

        return returnData(Response.SUCCESS.code(), message, data);
    }

    // ERROR
    public static ResultInfo fail() {

        return returnData(Response.ERROR.code(), Response.ERROR.message(), null);
    }

    public static ResultInfo fail(Integer code, String message) {

        return returnData(code, message, null);
    }

    public static<T> ResultInfo fail(Integer code, String message, T data) {

        return returnData(code, message, data);
    }
}
