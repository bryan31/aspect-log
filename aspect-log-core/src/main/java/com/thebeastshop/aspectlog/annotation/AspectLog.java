package com.thebeastshop.aspectlog.annotation;

import com.thebeastshop.aspectlog.convert.AspectLogConvert;

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

    String[] value() default {};

    String joint() default "-";

    String pattern() default "[{}]";

    Class<? extends AspectLogConvert> convert() default AspectLogConvert.class;

}
