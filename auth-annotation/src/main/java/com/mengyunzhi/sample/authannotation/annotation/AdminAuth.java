package com.mengyunzhi.sample.authannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权注解，用该注解标注的方法，拦截器不拦截
 * AdminOnly与Anonymous两注解使用该注解标注
 *
 * 原理：
 * AdminOnly注解 与 Ayonymous注解，分别使用了此注解进行注解，相当于注解的继承。
 * 在拦截器具中，如果我们发现了Auth注解，则认为发现了AdminOnly注解或Ayonymous注解，不进行拦截。
 * 在AOP中，我们对AdminOnly注解进行了拦截，达到了对技术机构权限认证的目的。
 * 我们未对Ayonymous注解进行任何拦截，从而达到了匿名访问某些方法的目的。
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminAuth {
}
