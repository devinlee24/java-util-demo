package com.devinlee.javautildemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP地址工具类
 *
 * @author devinlee
 */
public class AddressUtils {

    /**
     * 获取本机的内网ip地址
     *
     * @return
     * @throws SocketException
     */
    public static String getInnetIp() throws SocketException {

        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {

            return netip;
        } else {

            return localip;
        }
    }

    /**
     * 获取本机的外网ip地址
     *
     * @return
     */
    public static String getV4IP() {

        StringBuilder inputLine = new StringBuilder();
        BufferedReader in = null;
        String read = "";

        try {
            URL url = new URL("https://ip.chinaz.com");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {

                inputLine.append(read + "\r\n");
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");

        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {

            return m.group(1);
        }
        return null;
    }
}