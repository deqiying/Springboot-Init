package com.deqiying.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;

@SuppressWarnings(value = {"unused"})
public class SystemInfoUtils {
    /**
     * 当前系统属性
     */
    private static final Properties props = System.getProperties();

    private static final Runtime runtime = Runtime.getRuntime();

    private static String ip;

    private static String mac;

    private static String osVersion;
    private static String osName;

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

    /**
     * 获取当前操作系统版本号
     *
     * @return 当前操作系统版本号
     */
    public static String getOsVersion() {
        if (SystemInfoUtils.osVersion == null) {
            SystemInfoUtils.osVersion = props.getProperty("os.version");
        }
        return SystemInfoUtils.osVersion;
    }

    /**
     * 获取当前操作系统的名称
     *
     * @return 当前操作系统的名称
     */
    public static String getOsName() {
        if (SystemInfoUtils.osName == null) {
            SystemInfoUtils.osName = props.getProperty("os.name");
        }
        return SystemInfoUtils.osName;
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

    /**
     * 获取CPU核心数
     *
     * @return CPU核心数
     */
    public static int getCoreCount() {
        return runtime.availableProcessors();
    }

    /**
     * 获取 JVM 空闲内存（单位：bytes）
     *
     * @return 空闲内存
     */
    public static long getFreeMemory() {
        return runtime.freeMemory();
    }
    /**
     * 获取 JVM 试图使用的最大内存量（单位：bytes）
     *
     * @return 空闲内存
     */
    public static long getMaxMemory() {
        return runtime.maxMemory();
    }
    /**
     * 获取 JVM 内存总量（单位：bytes）
     *
     * @return 空闲内存
     */
    public static long getTotalMemory() {
        return runtime.totalMemory();
    }

}
