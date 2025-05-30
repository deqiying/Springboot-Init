package com.deqiying.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@SuppressWarnings("unused")
public class ImageWatermarkUtil {

    public enum Position {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    /**
     * 将水印图片添加到主图片上，返回byte数组
     *
     * @param mainImage      主图片
     * @param watermarkImage 水印图片
     * @param position          水印位置（角落）
     * @param margin            边缘距离
     * @return 包含水印的图片byte数组
     */
    public static byte[] addWatermark(BufferedImage mainImage, BufferedImage watermarkImage,
                                      Position position, int margin) throws IOException {
        // 计算水印位置
        Point watermarkPosition = calculatePosition(mainImage, watermarkImage, position, margin);

        // 添加水印
        addWatermarkToImage(mainImage, watermarkImage, watermarkPosition.x, watermarkPosition.y);

        // 将结果转换为byte数组
        return convertImageToByteArray(mainImage);
    }

    /**
     * 将水印图片添加到主图片上（指定具体坐标），返回byte数组
     *
     * @param mainImageUrl      主图片的URL
     * @param watermarkImageUrl 水印图片的URL
     * @param x                 水印x坐标
     * @param y                 水印y坐标
     * @return 包含水印的图片byte数组
     */
    public static byte[] addWatermark(String mainImageUrl, String watermarkImageUrl,
                                      int x, int y) throws Exception {
        BufferedImage mainImage = loadImage(UrlUtils.openUrl(mainImageUrl));
        BufferedImage watermarkImage = loadImage(UrlUtils.openUrl(watermarkImageUrl));

        // 添加水印
        addWatermarkToImage(mainImage, watermarkImage, x, y);

        // 将结果转换为byte数组
        return convertImageToByteArray(mainImage);
    }

    /**
     * 将BufferedImage转换为byte数组
     */
    private static byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    /**
     * 从URL加载图片
     */
    private static BufferedImage loadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        try (InputStream inputStream = connection.getInputStream()) {
            return loadImage(inputStream);
        }
    }

    /**
     * 从URL加载图片
     */
    private static BufferedImage loadImage(byte[] image) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            return loadImage(inputStream);
        }
    }
    /**
     * 从URL加载图片
     */
    private static BufferedImage loadImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    /**
     * 计算水印位置
     */
    private static Point calculatePosition(BufferedImage mainImage, BufferedImage watermarkImage,
                                           Position position, int margin) {
        int mainWidth = mainImage.getWidth();
        int mainHeight = mainImage.getHeight();
        int watermarkWidth = watermarkImage.getWidth();
        int watermarkHeight = watermarkImage.getHeight();

        int x, y;

        switch (position) {
            case TOP_LEFT:
                x = margin;
                y = margin;
                break;
            case TOP_RIGHT:
                x = mainWidth - watermarkWidth - margin;
                y = margin;
                break;
            case BOTTOM_LEFT:
                x = margin;
                y = mainHeight - watermarkHeight - margin;
                break;
            case BOTTOM_RIGHT:
                x = mainWidth - watermarkWidth - margin;
                y = mainHeight - watermarkHeight - margin;
                break;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }

        return new Point(x, y);
    }

    /**
     * 将水印绘制到主图片上
     */
    private static void addWatermarkToImage(BufferedImage mainImage, BufferedImage watermarkImage,
                                            int x, int y) {
        Graphics2D g2d = mainImage.createGraphics();
        try {
            // 设置透明度和抗锯齿
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 绘制水印
            g2d.drawImage(watermarkImage, x, y, null);
        } finally {
            g2d.dispose();
        }
    }

}
