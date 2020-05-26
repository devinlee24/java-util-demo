package com.devinlee.javautildemo.util;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类(请求客户端)
 */
public class AddressClientUtils {

    /**
     * 获取客户端真实IP
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {

        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");

        if (!Strings.isNullOrEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (!Strings.isNullOrEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}
