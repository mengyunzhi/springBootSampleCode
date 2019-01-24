package com.mengyunzhi.sample.authannotation.startup;

import com.mengyunzhi.sample.authannotation.annotation.AdminAuth;
import com.mengyunzhi.sample.authannotation.config.AuthAnnotationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxishuo on 2019-01-23
 * 监听容器启动，启动时获取所有注解，并放入HashMap
 * 去搜索相关资料，大量使用反射会带来严重的性能问题，所以只在初始化时使用，提高性能
 *
 * 经测试，可以获取注解的注解。
 * 如: @RestControllerAdvice，使用其继承的@ControllerAdvice作为过滤条件，是可以扫描到的
 * AnnotationUtils.getAnnotation: 该方法能获取注解的注解
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/AnnotationUtils.html
 */
@Component
public class InitAnnotationsConfig implements ApplicationListener<ContextRefreshedEvent> {

    // 基础包名
    private static final String basePackageName = "com.mengyunzhi.sample.authannotation";
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(InitAnnotationsConfig.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 初始化组件扫描Scanner，禁用默认的filter
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        // 添加过滤条件，要求组件上有RestController注解
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        // 在当前项目包下扫描所有符合条件的组件
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackageName)) {
            // 获取当前组件的完整类名
            String name = beanDefinition.getBeanClassName();
            try {
                // 利用反射获取相关类
                Class<?> clazz = Class.forName(name);
                // 初始化方法名List
                List<String> methodNameList = new ArrayList<>();
                // 获取当前类(不包括父类，所以要求控制器间不能继承)中所有声明方法
                for (Method method : clazz.getDeclaredMethods()) {
                    // 获取授权注解
                    AdminAuth adminAuth = AnnotationUtils.getAnnotation(method, AdminAuth.class);
                    // 如果该方法不被授权，则需要认证
                    if (adminAuth == null) {
                        // 添加到List中
                        methodNameList.add(method.getName());
                    }
                }
                // 添加到Map中
                AuthAnnotationConfig.getAnnotationsMap().put(clazz, methodNameList);
            } catch (ClassNotFoundException e) {
                logger.error("扫描注解配置时，发生了ClassNotFoundException异常");
            }
        }
    }
}
