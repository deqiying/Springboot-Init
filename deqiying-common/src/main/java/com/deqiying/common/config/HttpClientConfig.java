package com.deqiying.common.config;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpClient配置类
 *
 * @author deqiying
 */
@Slf4j
@Import(HttpClientPoolConfig.class)
@Configuration
public class HttpClientConfig {
    @Resource
    private HttpClientPoolConfig httpClientPoolConfig;

    @Bean("defaultRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 初始化RestTemplate,并加入spring的Bean工厂，由spring统一管理
     */
    @Primary
    @Bean(name = "httpClientTemplate")
    public RestTemplate httpClientTemplate(ClientHttpRequestFactory factory) {
        return createRestTemplate(factory);
    }

    /**
     * 创建HTTP客户端工厂
     */
    @Bean(name = "clientHttpRequestFactory")
    public ClientHttpRequestFactory clientHttpRequestFactory() {

        // maxTotalConnection 和 maxConnectionPerRoute 必须要配

        if (httpClientPoolConfig.getMaxTotalConnect() <= 0) {
            throw new IllegalArgumentException("invalid maxTotalConnection: " + httpClientPoolConfig.getMaxTotalConnect());
        }
        if (httpClientPoolConfig.getMaxConnectPerRoute() <= 0) {
            throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + httpClientPoolConfig.getMaxConnectPerRoute());
        }
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(httpClientPoolConfig.getConnectTimeout());
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setConnectionRequestTimeout(httpClientPoolConfig.getReadTimeout());
        // 从连接池获取请求连接的超时时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(httpClientPoolConfig.getConnectionRequestTimout());
        return clientHttpRequestFactory;
    }


    /**
     * 配置httpClient
     *
     * @return HttpClient
     */
    @Bean
    public HttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 自定义 SSL 策略
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                // 注册http和https请求
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", createSSLConnSocketFactory()).build();

        //使用Httpclient连接池的方式配置(推荐)，同时支持netty，okHttp以及其他http框架
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(httpClientPoolConfig.getMaxTotalConnect());
        // 同路由并发数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClientPoolConfig.getMaxConnectPerRoute());
        //配置连接池
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        // 重试次数
        httpClientBuilder.setRetryStrategy(new DefaultHttpRequestRetryStrategy(httpClientPoolConfig.getRetryTimes(), TimeValue.ofSeconds(1)));
        //设置默认请求头
        List<Header> headers = getDefaultHeaders();
        httpClientBuilder.setDefaultHeaders(headers);
        //设置长连接保持策略
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        return httpClientBuilder.build();
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> {
                return true;    // 信任所有证书
            });
        } catch (GeneralSecurityException e) {
            log.error("ssl config error !", e);
        }
        return sslsf;
    }


    /**
     * 设置请求头
     *
     * @return DefaultHeaders
     */
    private List<Header> getDefaultHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        return headers;
    }


    private RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);

        //我们采用RestTemplate内部的MessageConverter
        //重新设置StringHttpMessageConverter字符集，解决中文乱码问题
        modifyDefaultCharset(restTemplate);

        //设置错误处理器
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        return restTemplate;
    }

    /**
     * 修改默认的字符集类型为utf-8
     *
     * @param restTemplate 默认客户端
     */
    private void modifyDefaultCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        Charset defaultCharset = Charset.forName(httpClientPoolConfig.getCharset());
        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    }

}
