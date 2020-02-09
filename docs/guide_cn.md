# 1. 介绍

Aspect-log是一个日志切面框架，能通过简单的配置在你的日志中统一加上你的业务标识。方便进行日志的分析和定位。特性如下：

1.使用简单，不侵入业务代码。只需要在方法上配置标注。

2.支持log4j，logback，log4j2三种常见的日志框架。

3.配置极其简单。提供“一键配置”，自动识别日志框架。

4.在方法上配置，无论调用多深或者异步调用。也可以统一加上业务标识。

5.完全无性能损耗。



?> 项目提供3种框架的测试用例，可以pull下来试试



# 2. Springboot的Maven依赖

在你的项目中加上依赖，maven的GAV为：

```xml
<dependency>
  <groupId>com.thebeastshop</groupId>
  <artifactId>aspect-log-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
```

version为<font color=red>**1.0**</font>，目前中央仓库没有上传，需要自己编译打包



# 3. Spring的Maven依赖

在你的项目中加上依赖，maven的GAV为：

```xml
<dependency>
  <groupId>com.thebeastshop</groupId>
  <artifactId>aspect-log-core</artifactId>
  <version>${version}</version>
</dependency>
```

version为<font color=red>**1.0**</font>，目前中央仓库没有上传，需要自己编译打包



# 4. AspectLog的配置

提供2种配置方法。

## 4.1 一键配置方法

这种方式用javassit实现，只需要一句话就可以实现。

```java
@SpringBootApplication
public class Runner {

    static {AspectLogEnhance.enhance();}//进行日志增强，自动判断日志框架

    public static void main(String[] args) {
        try {
            SpringApplication.run(Runner.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (Throwable e) {
            	e.printStackTrace();
            }
        }
    }
}
```



!> 因为这里是用javassit实现，需要在jvm加载对应日志框架的类之前，进行字节码增强。所以这里用static块，并且Springboot/Spring的启动类中不能加入log定义。否则会不生效。如果你是用tomcat/jboss/jetty等容器启动的，则参照`4.2 Log框架配置文件增强`



## 4.2 Log框架配置文件增强

针对于主流的Log日志框架作了适配

### 4.2.1 Log4J配置文件增强

只需要把`layout`的实现类换掉就可以了

每个公司的Log4J的模板大同小异，这里只给出xml的例子

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration>
    <appender name="stdout" class="org.apache.log4j.ConsoleAppender">
		<layout class="com.thebeastshop.aspectlog.enhance.log4j.AspectLog4jPatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS} [%p] %m  >> %c:%L%n"/>
        </layout>
    </appender>
    <appender name="fileout" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="File" value="./logs/test.log"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS} [%p] %m  >> %c:%L%n"/>
        </layout>
    </appender>
    <root>
        <priority value="info" />
        <appender-ref ref="stdout"/>
        <appender-ref ref="fileout"/>
    </root>
</log4j:configuration>

```



### 4.2.2 Logback的配置文件增强

换掉`encoder`的实现类或者换掉`layout`的实现类就可以了

以下给出xml示例：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <property name="APP_NAME" value="logtest"/>
    <property name="LOG_HOME" value="./logs" />
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder class="com.thebeastshop.aspectlog.enhance.logback.AspectLogbackEncoder">
			  <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
		</encoder>
    </appender>
    <appender name="FILE"  class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>${LOG_HOME}/${APP_NAME}.log</File>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <FileNamePattern>${LOG_HOME}/${APP_NAME}.log.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <MaxHistory>30</MaxHistory>
            <maxFileSize>1000MB</maxFileSize>
        </rollingPolicy>
        <encoder class="com.thebeastshop.aspectlog.enhance.logback.AspectLogbackEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 日志输出级别 -->
    <root level="INFO">
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
    </root>
</configuration>

```



## 4.2.3 Log4J2的配置文件增强

log4J2由于是通过插件形式实现的，log4J2有自动扫描插件的功能。所以无需对配置文件做任何更改就能生效。



# 5 AspectLog的使用方法

在你的方法上加上`@AspectLog`标注。简单的例子如下：

```java
@AspectLog({"id"})
public void demo1(String id,String name){
  log.info("这是第一条日志");
  log.info("这是第二条日志");
  log.info("这是第三条日志");
  new Thread(() -> log.info("这是异步日志")).start();
}
```

假设id的值为'NO1234'，日志打出来的样子如下：

```
2020-02-08 20:22:33.945 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234] 这是第一条日志
2020-02-08 20:22:33.945 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234] 这是第二条日志
2020-02-08 20:22:33.945 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234] 这是第三条日志
2020-02-08 20:22:33.948 [Thread-3] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234] 这是异步日志
```



> `@AspectLog`标注支持多个参数:

```java
@AspectLog({"id","name"})
public void demo1(String id,String name){
  log.info("这是第一条日志");
  log.info("这是第二条日志");
  log.info("这是第三条日志");
  new Thread(() -> log.info("这是异步日志")).start();
}
```

假设传入id的值为'NO1234'，name为'jenny'，日志打出来的样子如下：

```
2020-02-08 22:09:40.101 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234-jenny] 这是第一条日志
2020-02-08 22:09:40.101 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234-jenny] 这是第二条日志
2020-02-08 22:09:40.102 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234-jenny] 这是第三条日志
2020-02-08 22:09:40.103 [Thread-3] INFO  com.thebeastshop.aspectlog.main.Demo - [NO1234-jenny] 这是异步日志
```



> `@AspectLog`支持自定pattern和多个参数的连接符

```java
@AspectLog(value = {"id","name"},pattern = "<-{}->",joint = "_")
public void demo(String id,String name){
  log.info("加了patter和joint的示例");
}
```

日志打出来的样子如下：

```
2020-02-08 22:09:40.103 [main] INFO  com.thebeastshop.aspectlog.main.Demo - <-NO1234_jenny-> 加了patter和joint的示例
```



> `@AspectLog`支持点操作符，适用于对象的取值，支持类型为业务对象和Map

```java
@AspectLog({"person.id","person.age","person.company.department.dptId"})
public void demo(Person person){
  log.info("多参数加多层级示例");
}
```

日志打出来的样子如下：

```
2020-02-08 22:09:40.110 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [31-25-80013] 多参数加多层级示例
```



> `@AspectLog`支持编程式设值

```java
public void demo(){
  AspectLogContext.putLogValue("[SO1001]");
  log.info("代码控制示例");
}
```

日志打出来的样子：

```
2020-02-08 22:09:40.110 [main] INFO  com.thebeastshop.aspectlog.main.Demo - [SO1001] 代码控制示例
```



# 6. 即将支持以下特性

* javaagent方式配置支持，完全不侵入项目
* 支持配置Converter，支持值的转换和计算
* 支持更多的基础类型，支持json的打印
* 支持log日志任意位置的配置



# 7.联系作者
![logo](media/wx.jpeg)

