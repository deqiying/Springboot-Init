package com.deqiying.common.utils.spring;

import com.deqiying.common.utils.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/13 22:53
 */
@SuppressWarnings(value = {"unused", "unchecked"})
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    @Override
    public synchronized void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (SpringUtils.beanFactory == null) {
            SpringUtils.beanFactory = beanFactory;
        }
    }

    @Override
    public synchronized void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 获取当前应用上下文
     *
     * @return ApplicationContext 当前应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return SpringUtils.applicationContext;
    }

    /**
     * 获取当前 BeanFactory
     *
     * @return BeanFactory
     */
    public static ConfigurableListableBeanFactory getConfigurableListableBeanFactory() {
        return SpringUtils.beanFactory;
    }

    /**
     * 动态向Spring注册Bean(单例)
     * <p>
     * 由{@link org.springframework.beans.factory.BeanFactory} 实现，通过工具开放API
     * <p>
     *
     * @param <T>      Bean类型
     * @param beanName 名称
     * @param bean     bean
     */
    public static <T> void registerBean(@NonNull String beanName, @NonNull T bean) {
        final ConfigurableListableBeanFactory factory = getConfigurableListableBeanFactory();
        factory.autowireBean(bean);
        factory.registerSingleton(beanName, bean);
    }

    /**
     * 注销bean
     * <p>
     * 将Spring中的bean注销，请谨慎使用
     *
     * @param beanName bean名称
     */
    public static void unregisterBean(@NonNull String beanName) {
        final ConfigurableListableBeanFactory factory = getConfigurableListableBeanFactory();
        if (factory instanceof DefaultSingletonBeanRegistry registry) {
            registry.destroySingleton(beanName);
        } else {
            throw new BeanExpressionException("Can not unregister bean, the factory is not a DefaultSingletonBeanRegistry!");
        }
    }

    /**
     * 获取对象
     *
     * @param name bean 名称
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException org.springframework.beans.BeansException
     */
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz 类型
     * @return bean实例
     * @throws BeansException org.springframework.beans.BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    /**
     * 通过name+class获取bean
     *
     * @param name  beanName
     * @param clazz bean的Class对象
     * @return 获取到的bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return beanFactory.getBean(name, clazz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 获取当前请求属性
     *
     * @return 请求属性
     */
    public static ServletRequestAttributes currentAttributes() {
        return (ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes());
    }

    /**
     * 获取当前请求
     *
     * @return 当前请求
     */
    public static HttpServletRequest currentRequest() {
        return currentAttributes().getRequest();
    }

    /**
     * 获取当前请求的响应体
     *
     * @return 当前请求的响应体
     */
    public static HttpServletResponse currentResponse() {
        return currentAttributes().getResponse();
    }

    /**
     * 获取当前访问请求的浏览器标识
     *
     * @return 浏览器标识
     */
    public static String getUserAgent() {
        return currentRequest().getHeader("User-Agent");
    }

    /**
     * 获取当前访问请求的IP地址
     *
     * @return IP地址
     */
    public static String getRemoteAddr() {
        return currentRequest().getRemoteAddr();
    }

    /**
     * 获取当前访问请求的Cookie
     *
     * @return Cookie
     */
    public static Cookie[] getCookies() {
        return currentRequest().getCookies();
    }

    /**
     * 获取当前访问请求的请求方式
     *
     * @return 当前访问请求的请求方式
     */
    public static String getRequestMethod() {
        return currentRequest().getMethod();
    }

    /**
     * 获取当前请求的访问协议
     *
     * @return 当前请求的访问协议
     */
    public static String getRequestScheme() {
        return currentRequest().getScheme();
    }

    /**
     * 获取当前请求的会话
     *
     * @return 当前请求的会话
     */
    public static HttpSession getSession() {
        return currentRequest().getSession();
    }

    /**
     * 获取当前请求的访问域名
     *
     * @return 当前请求的访问域名
     */
    public static String getServerName() {
        return currentRequest().getServerName();
    }

    /**
     * 获取当前请求的访问端口
     *
     * @return 当前请求的访问端口
     */
    public static int getServerPort() {
        return currentRequest().getServerPort();
    }

    /**
     * 获取当前访问请求的请求头
     *
     * @return 当前访问请求的请求头
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        HttpServletRequest request = currentRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }

    /**
     * 获取当前请求访问URL
     * 完整URL，包括域名、端口、路径
     *
     * @return 当前请求访问URL
     */
    public static String getRequestURL() {
        return currentRequest().getRequestURL().toString();
    }

    /**
     * 获取当前请求访问URI路径
     *
     * @return 当前请求访问URI路径
     */
    public static String getRequestURI() {
        return currentRequest().getRequestURI();
    }

    /**
     * 获取当前请求访问URI路径
     * 包括请求参数
     *
     * @return 当前请求访问URI路径
     */
    public static String getRequestURIWithQueryString() {
        String queryString = currentRequest().getQueryString();
        return currentRequest().getRequestURI() +
                (StringUtils.isBlank(queryString) ? "" : "?" + URLDecoder.decode(queryString, StandardCharsets.UTF_8));
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name bean名称
     * @return boolean
     * @throws NoSuchBeanDefinitionException org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * @param name bean名称
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean名称
     * @return bean别名
     * @throws NoSuchBeanDefinitionException org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取aop代理对象
     *
     * @param invoker 代理对象
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

    /**
     * 获取配置文件中的值
     *
     * @param key 配置文件的key
     * @return 当前的配置文件的值
     */
    public static String getRequiredProperty(String key) {
        return applicationContext.getEnvironment().getRequiredProperty(key);
    }

    /**
     * 获取当前项目的资源
     *
     * @param resource 资源路径
     * @return Resource 项目资源
     */
    public static Resource getResource(String resource) {
        return applicationContext.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + resource);
    }

    /**
     * 发布事件
     * Spring 4.2+ 版本事件可以不再是{@link ApplicationEvent}的子类
     *
     * @param event 待发布的事件
     */
    public static <T> void publishEvent(T event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }
}
