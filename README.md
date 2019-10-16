# payload-spring-boot
[![Build Status](https://travis-ci.org/deepexi/payload-spring-boot-starter.svg?branch=master)](https://travis-ci.org/deepexi/payload-spring-boot-starter)  [![codecov](https://codecov.io/gh/deepexi/payload-spring-boot-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/deepexi/payload-spring-boot-starter)

将项目中统一结果返回对象的相关内容抽离出来，封装成一个独立的starter，方便在Spring Boot至使用。

## 版本信息
支持Spring Boot 2.x的版本

     Spring Boot：2.1.9.RELEASE

## Getting Started
  实现自定义的Converter，然后引入starter坐标后，调用ConverterUtils的方法即可进行转化，
  具体使用参考[Test Demo](https://github.com/deepexi/payload-spring-boot-starter/tree/master/payload-spring-boot-starter-test)
  
  目前还在申请发布至Maven仓库，需要自行安装到本地仓库。
  
### 引入坐标
  
```xml
   <dependency>
     <groupId>com.deepexi</groupId>
     <artifactId>payload-spring-boot-starter</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
```
        
### 开启payload
  
```properties
#开启payload统一返回结果，不配置默认开启：true
deepexi.payload.enabled=true

#开启payload统一异常返回结果，不配做默认关闭：false  
deepexi.payload.error.enabled=false 

#自定义成功返回码 默认 1
deepexi.payload.code=1
```
   
### 使用

payload-starter主要依靠以下两个注解实现相应的功能，对这两个注解有相应的Handler处理。

```java
@Payload
@BizErrorResponseStatus("${ERROR-CODE}")
```
 
 
Controller *@RestController* 类上使用 **@Payload** 注解，自动对返回的数据进行包装。格式如下：

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

##### 异常处理

继承RuntimeException类，并加上 **@BizErrorResponseStatus("00000-0000-0001")** 注解，值为自定义的错误码。
格式如下
```json
{
    "success": false,
    "message": "No message available",
    "code": "00000-0000-0001"
}
```
