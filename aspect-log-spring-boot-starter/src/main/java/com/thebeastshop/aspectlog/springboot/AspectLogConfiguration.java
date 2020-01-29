package com.thebeastshop.aspectlog.springboot;

import com.thebeastshop.aspectlog.aop.AspectLogAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author Bryan.Zhang
 * @Date 2020/1/17
 * AspectLog的自动装配
 */
@Configuration
public class AspectLogConfiguration {

    @Bean
    public AspectLogAop aspectLogAop(){
        return new AspectLogAop();
    }
}
