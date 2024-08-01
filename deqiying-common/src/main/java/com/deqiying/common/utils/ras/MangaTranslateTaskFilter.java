package com.deqiying.common.utils.ras;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;


/**
 * @date 2024-05-13
 * 漫画翻译接口过滤器
 */
@Slf4j
@WebFilter(urlPatterns = {"/manga/submit/translateTask", "/manga/get/translateResult"})
public class MangaTranslateTaskFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String md5Str = RequestSignUtil.decryptSign(request, servletResponse);
        if (!StringUtils.hasText(md5Str)) {
            return;
        }
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
//        MangaTranslateTaskRbo mangaTranslateTaskRbo = JSONObject.parseObject(cachedBodyHttpServletRequest.getInputStream(), MangaTranslateTaskRbo.class);
//        if (RequestSignUtil.verifySignIsError(mangaTranslateTaskRbo, md5Str, servletResponse)) {
//            return;
//        }
        filterChain.doFilter(cachedBodyHttpServletRequest, response);
    }
}
