package com.devinlee.javautildemo.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类(请求客户端)
 */
@Slf4j
public class AddressClientUtils {

    /**
     * 获取客户端真实IP
     *
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

    /**
     * 获取IP地理信息
     *
     * @param key 腾讯位置服务key
     * @param ip
     * @return
     */
    public String getIpCity(String key, String ip) {

        String url = "https://apis.map.qq.com/ws/location/v1/ip?key=" + key + "&ip=" + ip;
        String result = "未知";
        try {
            String json = HttpUtil.get(url, 3000);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String status = jsonObject.get("status").getAsString();
            if ("0".equals(status)) {
                JsonObject adInfo = jsonObject.get("result").getAsJsonObject().get("ad_info").getAsJsonObject();
                String nation = adInfo.get("nation").getAsString();
                String province = adInfo.get("province").getAsString();
                String city = adInfo.get("city").getAsString();
                String district = adInfo.get("district").getAsString();
                if (StrUtil.isNotBlank(nation) && StrUtil.isBlank(province)) {
                    result = nation;
                } else {
                    result = province;
                    if (StrUtil.isNotBlank(city)) {
                        result += " " + city;
                    }
                    if (StrUtil.isNotBlank(district)) {
                        result += " " + district;
                    }
                }
            }
        } catch (Exception e) {
            log.info("获取IP地理信息失败");
        }
        return result;
    }
}
