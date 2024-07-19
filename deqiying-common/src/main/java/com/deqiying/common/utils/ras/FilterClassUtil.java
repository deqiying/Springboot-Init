package com.deqiying.common.utils.ras;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * 过滤器类工具
 *
 * @author maozheming
 * @date 2024-05-17
 */
public class FilterClassUtil {

    private final static String RESPONSE_TEMPLATE = "{\"code\":%s,\"message\":\"%s\",\"data\":null}";

    public static void printWriter(ServletResponse servletResponse, String code, String responseMsg) {

        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");
        try (PrintWriter out = new HttpServletResponseWrapper(
                (HttpServletResponse) servletResponse).getWriter()) {
            out.write(
                    String.format(RESPONSE_TEMPLATE, code, responseMsg)
                            .replaceAll("\n", "")
            );
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void printWriter(ServletResponse servletResponse, String responseMsg) {
        printWriter(servletResponse, "400", responseMsg);
    }

}
