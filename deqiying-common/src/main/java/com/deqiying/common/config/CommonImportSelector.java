package com.deqiying.common.config;

import com.deqiying.common.annotation.EnableCommon;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * common包扫描选择器
 *
 * @author qiying
 * @version 1.0
 * @since 2024/1/7 1:13
 */
public class CommonImportSelector implements ImportSelector {

    private static final Map<String, String> ALL_CONFIGURATION = new HashMap<>();
    static {
        // 维护 EnableCommon 注解的属性对应的类名
        ALL_CONFIGURATION.put("useCommon", CommonConfiguration.class.getName());
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 当存在注解 EnableCommon 时   取其useXxx 布尔值  true  导入 XxxConfiguration
        // 其它任何情况将不导入
        if (importingClassMetadata.hasAnnotation(EnableCommon.class.getName())) {
            List<String> importClassNames = new ArrayList<>();
            MultiValueMap<String, Object> attributes = importingClassMetadata.getAllAnnotationAttributes(EnableCommon.class.getName());
            assert attributes != null;
            attributes.forEach((key, value)->{
                if (value != null && !value.isEmpty()) {
                    var use = value.get(0);
                    if (use instanceof Boolean) {
                        if ((boolean) use) {
                            var s = ALL_CONFIGURATION.get(key);
                            if (s != null) {
                                importClassNames.add(s);
                            }
                        }
                    }
                }
            });
            return importClassNames.toArray(new String[0]);
        }
        return new String[0];
    }
}
