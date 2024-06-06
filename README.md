# 关于这个项目
这是一个Springboot项目的初始化模板，包含了一些常用的工具类、基类、网络请求等。

## 项目技术
- JDK 17
- Springboot 3.x
- Lombok
- Redis

## 项目结构
```
Springboot-Init
├── deqiying-common
│   ├── annotation
│   ├── config
│   ├── constant
│   ├── core
│   ├── utils
├── deqiying-framework
│   ├── advice
│   ├── annotation
│   ├── config
│   ├── enums
│   ├── exception
│   ├── interfaces
│   ├── utils
│   ├── vo
├── deqiying-web
│   ├── controller
```
## 模块说明
#### common
包含了一些常用的工具类，如：时间工具类、字符串工具类、网络请求工具类等。
同时声明了一些常用的bean，如：网络请求工具RestTemplate、Spring上下文工具类SpringUtils等。

#### framework
用于统一引入一些基础的依赖，如：lombok、spring-boot-starter-web、spring-boot-starter-data-redis等。
以及统一配置一些基础的配置，如：统一异常处理、统一返回结果等。
包含了一些常用的bean，如：RedisUtils、统一异常处理类、统一返回结果类等。

#### web
用于接口开发的模块，包含了一些常用的接口开发的工具类
以及一些常用的工具类使用示例。

# 为什么要做这个项目？
因为每次新建一个项目都需要重新进行模块的创建，较为麻烦。故而创建该项目，方便初始化项目。
尤其是在使用Springboot的时候，每次都需要引入一些基础的依赖，进行一些基础的配置，故而创建该项目。
对于Web项目，该项目也提供了一些常用的接口开发工具类，方便接口开发。

# 接下来有什么打算？
1：实现本地缓存工具类，其大概实现是支持 按过期时间、缓存大小进行旧缓存的自动清除，避免占用内存过大。
2：实现统一线程池管理，动态创建线程池，查看线程池情况。