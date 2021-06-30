package com.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author A jun
 * @ClassName com.com.member.member.common.annotation
 * @Description 自定义返回结果集结构
 * @Date 2018/4/9 21:11
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface OtherReturn {
}
