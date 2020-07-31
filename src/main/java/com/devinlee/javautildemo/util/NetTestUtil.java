package com.devinlee.javautildemo.util;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 网络测试工具
 *
 * @author devinlee
 * @version 1.0
 */
@Slf4j
public class NetTestUtil {

    /**
     * 测试ping主机地址的连通性
     *
     * @param hostname
     * @return
     */
    public static boolean ping(String hostname) {

        return ping(hostname, 5000);
    }

    /**
     * 测试ping主机地址的连通性
     *
     * @param hostname
     * @param timeout
     * @return
     */
    public static boolean ping(String hostname, int timeout) {
        try {

            return InetAddress.getByName(hostname).isReachable(timeout);

        } catch (IOException e) {

            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 测试telnet机器端口的连通性
     *
     * @param hostname
     * @param port
     * @return
     */
    public static boolean telnet(String hostname, int port) {

        return telnet(hostname, port, 5000);
    }

    /**
     * 测试telnet机器端口的连通性
     *
     * @param hostname
     * @param port
     * @param timeout
     * @return
     */
    public static boolean telnet(String hostname, int port, int timeout) {

        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(hostname, port), timeout); // 建立连接

            return socket.isConnected(); // 通过现有方法查看连通状态
        } catch (IOException e) {

            log.error(e.getMessage());        // 当连不通时，直接抛异常，异常捕获即可
        } finally {
            try {
                socket.close();   // 关闭连接
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return false;
    }
}

