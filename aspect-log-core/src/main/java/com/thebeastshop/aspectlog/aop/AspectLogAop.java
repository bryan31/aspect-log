package com.thebeastshop.aspectlog.aop;

import com.google.common.collect.Maps;
import com.sun.javafx.binding.StringFormatter;
import com.thebeastshop.aspectlog.annotation.AspectLog;
import com.thebeastshop.aspectlog.context.AspectLogContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bryan.Zhang
 * @Date 2020/1/15
 * 注解切面，用于拦截@AspectLogAop
 */
@Aspect
public class AspectLogAop {

    private final Logger log = LoggerFactory.getLogger(AspectLogAop.class);

    @Pointcut("@annotation(com.thebeastshop.aspectlog.annotation.AspectLog)")
    public void cut(){}

    @Around("cut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature)jp.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        Map<String,Object> paramNameValueMap = Maps.newHashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            paramNameValueMap.put(parameterNames[i],args[i]);
        }

        AspectLog aspectLog = method.getAnnotation(AspectLog.class);
        String[] aspectExpressions = aspectLog.value();
        String aspLogValuePattern = aspectLog.pattern().replaceAll("\\{\\}","{0}");
        String joint = aspectLog.joint();

        StringBuilder sb = new StringBuilder();
        for (String aspectExpression : aspectExpressions){
            String aspLogValueItem = getExpressionValue(aspectExpression,paramNameValueMap);
            if(StringUtils.isNotBlank(aspLogValueItem)){
                sb.append(aspLogValueItem);
                sb.append(joint);
            }
        }
        String aspLogValue = sb.toString();
        if(StringUtils.isNotBlank(aspLogValue)){
            aspLogValue = aspLogValue.substring(0,aspLogValue.length()-1);

            aspLogValue = MessageFormat.format(aspLogValuePattern,aspLogValue);
            AspectLogContext.putLogValue(aspLogValue);
        }

        try{
            return jp.proceed();
        }finally {
            AspectLogContext.remove();
        }
    }

    private String getExpressionValue(String expression, Object o){
        String[] expressionItems = expression.split("\\.");
        for(String item : expressionItems){
            if(String.class.isAssignableFrom(o.getClass())){
                return (String)o;
            }else if(Integer.class.isAssignableFrom(o.getClass())) {
                return ((Integer) o).toString();
            }else if(Long.class.isAssignableFrom(o.getClass())){
                return ((Long) o).toString();
            }else if(Double.class.isAssignableFrom(o.getClass())){
                return ((Double) o).toString();
            }else if(BigDecimal.class.isAssignableFrom(o.getClass())){
                return ((BigDecimal) o).toPlainString();
            }else if(Map.class.isAssignableFrom(o.getClass())){
                Object v = ((Map)o).get(item);
                if(v == null){
                    return null;
                }
                return getExpressionValue(getRemainExpression(expression,item),v);
            }else{
                try{
                    Object v = MethodUtils.invokeMethod(o,"get"+item.substring(0,1).toUpperCase() + item.substring(1));
                    if(v == null){
                        return null;
                    }
                    return getExpressionValue(getRemainExpression(expression,item),v);
                }catch (Exception e){
                    return null;
                }
            }
        }
        return null;
    }

    private String getRemainExpression(String expression, String expressionItem){
        if(expression.equals(expressionItem)){
            return expressionItem;
        }else{
            return expression.substring(expressionItem.length()+1);
        }
    }
}
