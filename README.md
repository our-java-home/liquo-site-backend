# liquo-site-backend

## 项目目录结构
```text
wine
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── javahome
│   │   │           └── wine
│   │   │               ├── aop                             // 全局响应切面增强
│   │   │               ├── api                             // api接口/controller层
│   │   │               │   └── v1                          // 版本号
│   │   │               │       └── HelloController.java
│   │   │               ├── config                          // 配置文件相关
│   │   │               ├── dto                             // 数据传输对象，用于前端参数映射
│   │   │               │   └── UserDTO.java
│   │   │               ├── exception                       // 全局异常处理类
│   │   │               ├── interceptor                     // 拦截器处理类
│   │   │               ├── mapper                          // 数据库接口相关
│   │   │               ├── model                           // 数据库实体类映射存储
│   │   │               ├── service                         // 业务逻辑接口层及其实现类
│   │   │               │   └── impl
│   │   │               ├── util                            // 工具类
│   │   │               └── vo                              // 全局数据返回对象
│   │   │               │    └── ResultDataVO.java
│   │   │               └── WineApplication.java            // 项目主启动类
│   │   └── resources                                       // 资源文件
│   │       ├── application-dev.yml                         // dev 开发环境
│   │       ├── application-prod.yml                        // prod 生产环境
│   │       ├── application-test.yml                        // test 测试环境
│   │       ├── application.yml                             // 主配置文件,根据active配置加载不同的环境,默认dev
│   │       ├── config
│   │       ├── DataSource                                      // 用于测试的数据库文件
│   │       ├── static
│   │       │   └── doc                                     // 接口文档输出目录               
│   │       ├── mapper                                      // 自定义mapper文件
│   │       └── templates
│   └── test                                                // 单元测试相关
│       └── java
│           └── com
│               ├── javahome
│               │   └── wine
│               │       └── WineApplicationTests.java
│               └── mall
├── .gitignore
└── pom.xml

```
### 技术架构
![](C:/Users/11460/AppData/Roaming/Typora/typora-user-images/image-20230625024949615.png)

### 项目启动

> 下载代码到本地IDE后,确保本地JDK版本为17,依次打开src/main/java/com/javahome/wine下的wineApplication类，点击main函数行左侧run按钮即可启动，
> 默认加载resources文件下的application-dev.yml配置文件,端口为8094,如需加载其它文件,请在application.yml自行修改。

### 简单示例
> src/main/java/com/javahome/wine/api/v1目录下的HelloController提供了一个简单的示例,使用Restful注解,即分别对应Get,Post,Put,Delete
> 除Get方法外， 其余三种接口访问请使用诸如Postman的工具进行测试。
> UserController 提供了一个基本的CRUD示例，可将src/main/resources/DataSource/user.sql文件导入本地数据库中进行调试。

### 日志输出

> 在添加@sl4j的类中可使用log方式打印日志，错误信息使用log.error,普通信息使用log.info。

### 参数校验

> 项目已集成参数校验,可在需要校验的字段上加上对应注解来确定参数合法性,错误的信息将被异常处理器捕获并返回。

### 全局异常

> src/main/java/com/javahome/wine/exception目录下具有全局异常处理器,当需要抛出异常时,请使用throw抛出BusinessException对象，相关的
> 异常参数位于ExceptionCodeEnum中,请尽量使用已定义错误码返回异常信息。

### 全局响应
> src/main/java/com/javahome/wine/vo目录下的ResultDataVO用于统一返回处理结果,其格式如下。
> 现已支持返回自定义的格式数据，并且无需显式调用success方法进行数据的返回。

```json
{
    "status": true,
    "code": 0,
    "message": "操作成功",
    "data": "Hello"
}
```