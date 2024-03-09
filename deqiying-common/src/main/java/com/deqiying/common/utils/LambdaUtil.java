package com.deqiying.common.utils;

import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SuppressWarnings(value = {"unused", "unchecked"})
public class LambdaUtil {

    public static final Map<Class<?>, PropertyDescriptor[]> cache = new ConcurrentHashMap<>();

    /**
     * 通过bean属性名称，获取其getter方法的函数式调用 Function<T, ?>
     *
     * @param clazz 目标类
     * @param prop  字段名称
     * @param <T>   目标类型
     * @return Function<T, ?>
     * @throws Throwable 可能发生的异常
     */
    public static <T> Function<T, ?> getLambdaGetter(Class<T> clazz, String prop) throws Throwable {
        PropertyDescriptor[] beanGetters;
        if (cache.containsKey(clazz)) {
            beanGetters = cache.get(clazz);
        } else {
            beanGetters = ReflectUtils.getBeanGetters(clazz);
            cache.put(clazz, beanGetters);
        }
        var lookup = MethodHandles.lookup();
        var optional = Arrays.stream(beanGetters)
                .filter(pd -> pd.getName().equals(prop))
                .findFirst();
        if (optional.isPresent()) {
            // 反射获取getter方法
            var readMethod = optional.get().getReadMethod();
            // 拿到方法句柄
            final MethodHandle methodHandle = lookup.unreflect(readMethod);
            // 创建动态调用链
            var callSite = LambdaMetafactory.altMetafactory(
                    lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    methodHandle,
                    MethodType.methodType(readMethod.getReturnType(), clazz),
                    LambdaMetafactory.FLAG_SERIALIZABLE
            );
            return (Function<T, ?>) callSite.getTarget().invokeExact();
        }
        return null;
    }

}
