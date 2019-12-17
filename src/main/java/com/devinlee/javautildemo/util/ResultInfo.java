package com.devinlee.javautildemo.util;

import java.io.Serializable;

/**
 * 返回结果封装
 */
public class ResultInfo<T> implements Serializable {

    private Integer retCode;

    private String retMsg;

    private T data;

    public ResultInfo(Integer retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }
}
