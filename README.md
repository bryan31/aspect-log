## 概述
Aspect-log是一个日志切面工具，能通过简单的配置在你的日志中统一加上你的业务标识。方便进行日志的分析和定位。

## 特性
* 使用简单，不侵入业务代码。只需要在方法上配置标注
* 支持log4j，logback，log4j2三种常见的日志框架
* 配置极其简单。提供“一键配置”，自动识别日志框架
* 在方法上配置，无论调用多深或者异步调用。也可以统一加上业务标识
* 完全无性能损耗

详细文档请点击：[中文文档](http://yomahub.com/aspectlog)

## 效果简单示例
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

