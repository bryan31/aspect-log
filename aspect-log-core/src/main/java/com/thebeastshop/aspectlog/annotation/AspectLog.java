package com.thebeastshop.aspectlog.annotation;

import java.lang.annotation.*;

/**
 * @author Bryan.Zhang
 * @Date 2020/1/15
 * 切面日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AspectLog {

    String[] value();

    String joint() default "-";

    String pattern() default "[{}]";

}
