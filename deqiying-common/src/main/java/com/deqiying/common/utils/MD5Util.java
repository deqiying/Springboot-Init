package com.deqiying.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author maozheming
 * @date 2024-05-13
 * md5 工具类
 */
public class MD5Util {

    public static String md5(String input) {
        try {
            // 获取 MD5 摘要算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将输入字符串转换为字节数组，并进行摘要计算
            byte[] messageDigest = md.digest(input.getBytes());
            // 将字节数组转换为十六进制字符串
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            // 如果十六进制字符串长度不够，前面补0
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
