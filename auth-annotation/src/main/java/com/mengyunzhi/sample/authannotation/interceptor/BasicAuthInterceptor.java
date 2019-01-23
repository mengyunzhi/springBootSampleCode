package com.mengyunzhi.sample.authannotation.interceptor;

import com.mengyunzhi.sample.authannotation.config.AuthAnnotationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhangxishuo on 2019-01-23
 * 基本BasicAuth拦截器
 */
@Component
public class BasicAuthInterceptor extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(BasicAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        logger.debug("打除BasicErrorController");
        if (((HandlerMethod) handler).getBean() instanceof BasicErrorController) {
            return true;
        }

        logger.debug("获取当前请求方法的组件类型");
        Class<?> clazz = handlerMethod.getBeanType();

        logger.debug("获取当前处理请求的方法名");
        String methodName = handlerMethod.getMethod().getName();

        logger.debug("获取当前类中需认证的方法名");
        List<String> authMethodNames = AuthAnnotationConfig.getAnnotationsMap().get(clazz);

        logger.debug("如果List为空或者不包含在认证方法中，释放拦截");
        if (authMethodNames == null || !authMethodNames.contains(methodName)) {
            return true;
        }

        logger.debug("进行用户认证");
        //noinspection UnnecessaryLocalVariable
        boolean result = false;

        // 用户认证

        //noinspection ConstantConditions
        return result;
    }
}
