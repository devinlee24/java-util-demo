package com.devinlee.javautildemo.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 客户端信息的工具类
 * @author devinlee
 * @date 2019/12/17 14:51
 **/
public class NetworkUtils {

    /**
     * 获取真实的客户端IP地址
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request)
            throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 获取客户端各种信息
     * @param request
     * @throws Exception
     */
    public void get(HttpServletRequest request) throws Exception{

        System.out.println("浏览器基本信息："+request.getHeader("user-agent"));
        System.out.println("客户端系统名称："+System.getProperty("os.name"));
        System.out.println("客户端系统版本："+System.getProperty("os.version"));
        System.out.println("客户端操作系统位数："+System.getProperty("os.arch"));
        System.out.println("HTTP协议版本："+request.getProtocol());
        System.out.println("请求编码格式："+request.getCharacterEncoding());
        System.out.println("Accept："+request.getHeader("Accept"));
        System.out.println("Accept-语言："+request.getHeader("Accept-Language"));
        System.out.println("Accept-编码："+request.getHeader("Accept-Encoding"));
        System.out.println("Connection："+request.getHeader("Connection"));
        System.out.println("Cookie："+request.getHeader("Cookie"));
        System.out.println("客户端发出请求时的完整URL"+request.getRequestURL());
        System.out.println("请求行中的资源名部分"+request.getRequestURI());
        System.out.println("请求行中的参数部分"+request.getRemoteAddr());
        System.out.println("客户机所使用的网络端口号"+request.getRemotePort());
        System.out.println("WEB服务器的IP地址"+request.getLocalAddr());
        System.out.println("WEB服务器的主机名"+request.getLocalName());
        System.out.println("客户机请求方式"+request.getMethod());
        System.out.println("请求的文件的路径"+request.getServerName());
        System.out.println("请求体的数据流"+request.getReader());
        BufferedReader br=request.getReader();
        String res = "";
        while ((res = br.readLine()) != null) {
            System.out.println("request body:" + res);
        }
        System.out.println("请求所使用的协议名称"+request.getProtocol());
        System.out.println("请求中所有参数的名字"+request.getParameterNames());
        Enumeration enumNames= request.getParameterNames();
        while (enumNames.hasMoreElements()) {
            String key = (String) enumNames.nextElement();
            System.out.println("参数名称："+key);
        }
    }
}
