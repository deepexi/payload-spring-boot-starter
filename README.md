# payload-spring-boot-starter
[![Build Status](https://travis-ci.org/deepexi/payload-spring-boot-starter.svg?branch=master)](https://travis-ci.org/deepexi/payload-spring-boot-starter) 
[![codecov](https://codecov.io/gh/deepexi/payload-spring-boot-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/deepexi/payload-spring-boot-starter)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.deepexi/pojo-converter-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.deepexi%22%20AND%20a:%22pojo-converter-spring-boot-starter%22)

将项目中统一结果返回对象的相关内容抽离出来，封装成一个独立的starter，方便在Spring Boot至使用。

## 版本信息
基于 **Spring Boot：2.1.9.RELEASE** 构建

## Getting Started
  实现自定义的Converter，然后引入starter坐标后，调用ConverterUtils的方法即可进行转化，
  具体使用参考[Test Demo](https://github.com/deepexi/payload-spring-boot-starter/tree/master/src/test/java/com/deepexi/payload)
  
  
### 引入坐标
  
```xml
<dependency>
    <groupId>com.github.deepexi</groupId>
    <artifactId>payload-spring-boot-starter</artifactId>
    <version>1.0.0-release</version>
</dependency>
```
     
#### 配置
见配置文件中 **spring.mvc.payload** 属性的自动提示
可自定义返回属性名称
```yaml
spring:
  mvc:
    payload:
      payload-map:
        code: code
        success: success
        message: message
        payload: payload
```
      

 ##### 统一结果 @Payload
  
Controller *@RestController* 类上使用 **@Payload** 注解，自动对返回的数据进行包装。

 ```java
@Payload
@RequestMapping("/users")
@RestController
public class MyController {

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
      return user;
    }

}

```
响应结果格式如下：
```json
{
  "code": "1",
  "payload": {
     "k1": "v1",
     "k2": "v2"
  },
  "success": true
}
```

##### 异常处理  @BizErrorResponseStatus

继承RuntimeException类，并加上 **@BizErrorResponseStatus("00000-0000-0001")** 注解，值为自定义的错误码，
异常消息参数message也会一同响应返回。

```java
@BizErrorResponseStatus("00000-0000-0001")
public class CustomerException extends RuntimeException {
   public CustomerException(String message){
        super(message);
   }
}
```

响应结果格式如下：
```json
{
    "success": false,
    "message": "message",
    "code": "00000-0000-0001"
}
```


### TODO

**扩展返回字段**
