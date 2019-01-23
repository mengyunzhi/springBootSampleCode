package com.mengyunzhi.sample.authannotation.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxishuo on 2019-01-23
 * 认证注解配置
 */
public class AuthAnnotationConfig {

    /**
     * 私有注解配置Map
     * 该Map中存储需要认证的“用户”才能访问的方法
     * 即除了管理员与匿名注解的方法
     */
    private static Map<Class<?>, List<String>> annotationsMap = new HashMap<>();

    /**
     * 获取注解配置Map
     */
    public static Map<Class<?>, List<String>> getAnnotationsMap() {
        return annotationsMap;
    }
}
