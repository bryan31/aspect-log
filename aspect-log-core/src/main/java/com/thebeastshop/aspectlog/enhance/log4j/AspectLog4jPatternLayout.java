package com.thebeastshop.aspectlog.enhance.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * @author Bryan.Zhang
 * @Date 2020/1/22
 */
public class AspectLog4jPatternLayout extends PatternLayout {
    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new AspectLog4jPatternParser(pattern);
    }
}
