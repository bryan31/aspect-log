package com.thebeastshop.aspectlog.enhance.log4j;

import com.thebeastshop.aspectlog.context.AspectLogContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Bryan.Zhang
 * @Date 2020/1/22
 */
public class AspectLog4jPatternConverter extends PatternConverter {
    @Override
    protected String convert(LoggingEvent loggingEvent) {
        String logValue = AspectLogContext.getLogValue();
        if(StringUtils.isNotBlank(logValue)){
            return logValue + " " + loggingEvent.getRenderedMessage();
        }else{
            return loggingEvent.getRenderedMessage();
        }
    }
}
