package com.deqiying.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包扫描工具类
 *
 * @author deqiying
 * @since 2024/9/4
 */
@Slf4j
public class PackageScanner {

    /**
     * 扫描包中的类
     *
     * @param packageName 包名
     * @param recursive   是否递归扫描子目录
     * @param filter      过滤器
     * @return 类集合
     */
    public static Set<Class<?>> scanClass(String packageName, boolean recursive, ClassFilterApi filter) {
        Set<Class<?>> resultSet = new HashSet<>();

        if (packageName == null || packageName.isEmpty()) {
            return resultSet;
        }

        log.info("Scanning package: {}", packageName);
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        log.info("ClassLoader: {}", classLoader);
        try {
            Enumeration<URL> urls = classLoader.getResources(packagePath);
            log.info("URL: {}", urls);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                log.info("URL: {},Protocol: {}", url, protocol);
                if ("file".equalsIgnoreCase(protocol)) {
                    // 扫描文件目录中的类
                    String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                    scanClassFromDir(resultSet, filePath, packageName, recursive, filter);
                } else if ("jar".equalsIgnoreCase(protocol)) {
                    // 扫描 JAR 文件中的类
                    scanClassFromJar(resultSet, url, packageName, recursive, filter);
                }
            }
        } catch (IOException e) {
            log.error("Error scanning package: {}", packageName, e);
        }

        return resultSet;
    }

    /**
     * 从文件目录中扫描类
     *
     * @param resultSet   结果集
     * @param filePath    文件路径
     * @param packageName 包名
     * @param recursive   是否递归扫描子目录
     * @param filter      过滤器
     */
    private static void scanClassFromDir(Set<Class<?>> resultSet, String filePath, String packageName, boolean recursive, ClassFilterApi filter) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && recursive) {
                    // 递归扫描子目录
                    scanClassFromDir(resultSet, file.getAbsolutePath(), packageName + "." + file.getName(), recursive, filter);
                } else if (file.getName().endsWith(".class")) {
                    // 加载类
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    addClass(resultSet, className, filter);
                }
            }
        }
    }

    /**
     * 从 JAR 文件中扫描类
     *
     * @param resultSet   结果集
     * @param url         JAR 文件 URL
     * @param packageName 包名
     * @param recursive   是否递归扫描子目录
     * @param filter      过滤器
     */
    private static void scanClassFromJar(Set<Class<?>> resultSet, URL url, String packageName, boolean recursive, ClassFilterApi filter) {
        try {
            // 打开 Jar 文件连接
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(packageName.replace('.', '/'))) {
                    // 匹配包名
                    int idx = name.lastIndexOf('/');
                    if ((idx != -1 || recursive) && name.endsWith(".class") && !entry.isDirectory()) {
                        // 匹配类名-去掉 .class 后缀
                        String className = name.substring(0, name.length() - 6).replace('/', '.');
                        addClass(resultSet, className, filter);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error scanning JAR file: {}", url, e);
        }
    }

    /**
     * 添加类
     *
     * @param resultSet 结果集
     * @param className 类名称
     * @param filter    过滤器
     */
    private static void addClass(Set<Class<?>> resultSet, String className, ClassFilterApi filter) {
        try {
            Class<?> clazz = Class.forName(className);
            if (filter == null || filter.accept(clazz)) {
                resultSet.add(clazz);
            }
        } catch (ClassNotFoundException e) {
            log.error("Error loading class: {}", className, e);
        }
    }

    // 自定义过滤器接口
    public interface ClassFilterApi {
        boolean accept(Class<?> clazz);

        default boolean isNotAccept(Class<?> clazz) {
            return !accept(clazz);
        }
    }
}