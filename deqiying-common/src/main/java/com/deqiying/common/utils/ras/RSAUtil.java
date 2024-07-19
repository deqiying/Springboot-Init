package com.deqiying.common.utils.ras;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    public static String decrypt(String encryptedText, String privateKeyString) throws Exception {
        // 使用Base64解码私钥字符串
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);

        // 使用PKCS8EncodedKeySpec生成私钥对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 使用Cipher对象对密文进行解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        // 将解密后的字节数组转换成字符串并返回
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
