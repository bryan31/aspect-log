package com.thebeastshop.aspectlog.main;

import com.thebeastshop.aspectlog.annotation.AspectLog;
import com.thebeastshop.aspectlog.context.AspectLogContext;
import com.thebeastshop.aspectlog.vo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Demo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @AspectLog({"id","name"})
    public void demo1(String id,String name){
        log.info("最基本的示例");
        new Thread(() -> log.info("这是异步日志")).start();
    }

    @AspectLog(value = {"id","name"},pattern = "<-{}->",joint = "_")
    public void demo2(String id,String name){
        log.info("加了patter和joint的示例");
    }

    @AspectLog({"person.id","person.age","person.company.department.dptId"})
    public void demo3(Person person){
        log.info("多层级示例");
    }

    public void demo4(){
        AspectLogContext.putLogValue("[SO1001]");
        log.info("代码控制示例");
    }
}
