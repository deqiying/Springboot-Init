package com.deqiying.common.utils;

import lombok.Getter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;

@Getter
public class SystemInfoUtils {
    /**
     * 当前系统属性
     */
    private static final Properties props = System.getProperties();

    private static String ip;

    private static String mac;

    // 获取当前设备的ip地址
    public static String getIp() {
        if (SystemInfoUtils.ip == null) {
            SystemInfoUtils.ip = getIPAddress();
        }
        return SystemInfoUtils.ip;
    }

    // 获取当前设备的mac地址
    public static String getMac() {
        if (SystemInfoUtils.mac == null) {
            SystemInfoUtils.mac = getMACAddress();
        }
        return SystemInfoUtils.mac;
    }

    // 获取当前设备的主机名
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    // 获取当前设备的IP地址
    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && !addr.isLinkLocalAddress() && addr.isSiteLocalAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    // 获取当前设备的MAC地址
    public static String getMACAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                byte[] mac = iface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
                    }
                    return sb.toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }


}
