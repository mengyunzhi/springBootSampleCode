package com.mengyunzhi.springbootsamplecode.multiquery.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 范围查询 -- 开始
 * @author panjie
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeginQueryParam {
    // 以应的查询字段
    String name();
}
