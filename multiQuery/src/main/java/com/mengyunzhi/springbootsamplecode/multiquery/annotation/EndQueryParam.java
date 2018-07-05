package com.mengyunzhi.springbootsamplecode.multiquery.annotation;

/**
 * 范围查询 -- 结束
 * @author panjie
 */
public @interface EndQueryParam {
    // 以应的查询字段
    String name() default "";
}
