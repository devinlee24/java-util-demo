package com.devinlee.javautildemo.util;

/**
 * 响应状态枚举
 * @author devinlee
 */
public enum ResponseCode {

    /* 成功状态码 */
    SUCCESS(0,"SUCCESS"),

    /* 参数错误：1001-1999 */
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺少"),

    /* 用户错误：2001-2999 */
    USER_NOT_LOGGED_IN(2001,"用户未登录，访问的路径需要验证，请登录"),
    USER_LOGIN_ERROR(2002,"账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账号已被禁用"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),


    /* 区间表示接口异常：3000-3999 */
    ERROR(3001,"系统异常");

    ResponseCode(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public Integer retCode() {
        return retCode;
    }

    public String retMsg() {
        return retMsg;
    }

    private Integer retCode;

    private String retMsg;
}
