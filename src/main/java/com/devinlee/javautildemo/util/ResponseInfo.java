package com.devinlee.javautildemo.util;

/**
 * 返回结果封装
 */
public class ResponseInfo<T> {

    private Integer retCode;

    private String retMsg;

    private T data;
}
