package com.mengyunzhi.springbootsamplecode.multiquery.annotation;

/**
 * 范围查询 -- 开始
 * @author panjie
 */
public @interface BeginQueryParam {
    // 以应的查询字段
    String name() default "";
}
