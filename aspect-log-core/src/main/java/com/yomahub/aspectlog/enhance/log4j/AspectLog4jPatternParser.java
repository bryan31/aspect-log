package com.yomahub.aspectlog.enhance.log4j;

import org.apache.log4j.helpers.PatternParser;
/**
 * @author Bryan.Zhang
 * @Date 2020/1/22
 */
public class AspectLog4jPatternParser extends PatternParser {
    public AspectLog4jPatternParser(String pattern) {
        super(pattern);
    }

    @Override
    protected void finalizeConverter(char c) {
        if ('m' == c) {
            addConverter(new AspectLog4jPatternConverter());
        } else {
            super.finalizeConverter(c);
        }
    }
}
