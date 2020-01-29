package com.thebeastshop.aspectlog.enhance.logback;

import ch.qos.logback.classic.PatternLayout;

/**
 * @author Bryan.Zhang
 * @Date 2020/1/19
 */
public class AspectLogbackLayout extends PatternLayout {
    static {
        defaultConverterMap.put("m", AspectLogbackConverter.class.getName());
        defaultConverterMap.put("msg", AspectLogbackConverter.class.getName());
        defaultConverterMap.put("message", AspectLogbackConverter.class.getName());
    }
}
