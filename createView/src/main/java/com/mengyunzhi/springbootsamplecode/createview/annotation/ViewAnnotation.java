package com.mengyunzhi.springbootsamplecode.createview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author panjie
 */
@Target({ElementType.TYPE, ElementType.FIELD})     // 作用于接口，类，枚举，注解上; 作用于字段上
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewAnnotation {
    String name() default "";
    String sql() default "";
}
