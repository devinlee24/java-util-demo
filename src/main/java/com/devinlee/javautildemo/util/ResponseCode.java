package com.devinlee.javautildemo.util;

/**
 * 状态枚举
 * @author devinlee
 */
public enum ResponseCode {

    /* 成功状态码 */
    SUCCESS(1,"SUCCESS"),

    /* 参数错误：1001-1999 */
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺少");

    /* 用户错误：2001-2999 */

    /* 区间表示接口异常：3000-3999 */

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
