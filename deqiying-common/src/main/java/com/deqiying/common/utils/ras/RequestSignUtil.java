package com.deqiying.common.utils.ras;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.deqiying.common.utils.MD5Util;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author maozheming
 * @date 2024-06-12
 * 过滤器类要使用的签名工具类
 */
public class RequestSignUtil {

    public static String decryptSign(HttpServletRequest request, ServletResponse servletResponse) {
        String sign = request.getHeader("sign");
        if (!StringUtils.hasText(sign)) {
//            FilterClassUtil.printWriter(servletResponse, "sign错误");
            return null;
        }
        String md5Str;
        try {
//            md5Str = RSAUtil.decrypt(sign, MangaImgTranslateConstant.RSA_PRIVATE_KEY);
            md5Str = "";
        } catch (Throwable e) {
            FilterClassUtil.printWriter(servletResponse, "sign错误");
            return null;
        }
        return md5Str;
    }

    public static boolean verifySignIsError(Object obj, String md5Str, ServletResponse servletResponse) {
        JSONObject json = (JSONObject) JSON.toJSON(obj);
        Map<String, Object> treeMap = new TreeMap<>(json);
        String jsonString = JSON.toJSONString(treeMap);
        String md5 = MD5Util.md5(jsonString);
        if (!md5.equals(md5Str)) {
            FilterClassUtil.printWriter(servletResponse, "sign错误");
            return true;
        }
        return false;
    }

}
