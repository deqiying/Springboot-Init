package com.deqiying.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件指纹工具类
 *
 * @author qiying
 * @since 2024-11-13
 */
public class FileFingerprintUtil {
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * 生成文件指纹。
     *
     * @param filePath 文件路径。
     * @return 文件指纹。
     * @throws IOException              如果发生文件读取错误。
     * @throws NoSuchAlgorithmException 如果指定的哈希算法不存在。
     */
    public static String generateFingerprint(Path filePath) throws IOException, NoSuchAlgorithmException {
        // 使用 try-with-resources 自动关闭文件输入流
        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            return generateFingerprint(fis);
        }
    }

    /**
     * 生成文件指纹。
     *
     * @param inputStream 文件输入流。
     * @return 文件指纹。
     */
    public static String generateFingerprint(InputStream inputStream) {
        MessageDigest messageDigest = null;
        try {
            // 初始化 SHA-256 消息摘要
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            // 读取文件内容
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return bytesToHex(messageDigest.digest());
        } catch (Throwable ignored) {

        }
        return null;
    }

    /**
     * 将字节数组转换为十六进制字符串。
     *
     * @param bytes 字节数组。
     * @return 十六进制字符串。
     */
    public static String bytesToHex(byte[] bytes) {
        // 字节数组转换成的十六进制字符串
        if (bytes == null) {
            return null;
        }
        // 为了避免扩容，直接指定足够的初始容量。SHA-256 哈希值通常有 64 个字符
        // 每个字节转为两个字符
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
