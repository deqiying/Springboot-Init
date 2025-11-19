package com.deqiying.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class UrlUtils {
    /**
     * 判断字符串是否是一个有效的URL
     *
     * @param url 要判断的字符串
     * @return 如果是有效URL则返回true，否则返回false
     */
    public static boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 尝试打开一个URL链接，如果响应类型是文件或流则返回InputStream，否则返回null
     *
     * @param url 要打开的URL
     * @return InputStream 如果是文件或流；否则返回null
     * @throws Exception 如果URL无效或请求失败
     */
    public static InputStream openUrl(String url) throws Exception {
        // 打开连接
        HttpURLConnection connection = openUrlConnection(url);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return connection.getInputStream();
        } else {
            throw new Exception("无法打开URL,响应码: " + responseCode);
        }
    }

    /**
     * 尝试下载一个URL链接
     *
     * @param url 要打开的URL
     * @return byte[] 下载好的字节流
     * @throws Exception 如果URL无效或请求失败
     */
    public static byte[] downloadUrl(String url) throws Exception {
//        try (InputStream inputStream = openUrl(url);
//             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            // 缓冲区
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//            return outputStream.toByteArray();
//        }
        return null;
    }

    /**
     * 尝试打开一个URL链接，返回HttpURLConnection对象
     *
     * @param urlStr 要打开的URL
     * @return HttpURLConnection 如果是文件或流；否则返回null
     * @throws Exception 如果URL无效或请求失败
     */
    public static HttpURLConnection openUrlConnection(String urlStr) throws Exception {
        URI uri = createEncodedUri(urlStr);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10 * 60 * 1000);
        connection.setReadTimeout(0);
        return connection;
    }

    /**
     * 创建一个编码后的URI对象
     *
     * @param urlStr 要打开的URL
     * @return URI 编码后的URI对象
     * @throws Exception 如果URL无效或请求失败
     */
    private static URI createEncodedUri(String urlStr) throws Exception {
        try {
            return new URI(encodeUrlIfNeeded(urlStr));
        } catch (URISyntaxException e) {
            throw new MalformedURLException("处理后的URL无效: " + e.getMessage());
        }
    }

    private static String encodeUrlIfNeeded(String url) {
        // 检查是否包含非ASCII字符或空格
        Pattern pattern = Pattern.compile("[^\\x00-\\x7F]|\\s");
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            return url; // 无需编码，直接返回原始URL
        }

        // 分割URL，保留协议和路径结构
        try {
            StringBuilder encodedUrl = new StringBuilder();
            String[] parts = url.split("/", -1);
            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    // 保留协议部分（如https:）
                    encodedUrl.append(parts[i]);
                } else {
                    // 对路径部分进行编码
                    String encodedPart = URLEncoder.encode(parts[i], StandardCharsets.UTF_8)
                            .replace("+", "%20"); // 将空格编码为%20
                    encodedUrl.append("/").append(encodedPart);
                }
            }
            return encodedUrl.toString();
        } catch (Exception e) {
            // 如果编码失败，返回原始URL
            return url;
        }
    }


}