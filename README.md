# dev-lite

各种开发工具、技术栈集成实验田

目标：随拿随用的经过测试的、可复用的 工具集

## 项目结构

dev-lite 项目包含以下模块：

| 模块 | 描述 | 功能 |
|------|------|------|
| c1-orm-lib | ORM框架集成 | 包含JPA、MyBatis、ShardingJDBC等ORM框架的示例 |
| c2-auth-lib | 认证授权 | 包含JWT登录认证的实现 |
| c3-async-lib | 异步处理 | 包含CompletableFuture、RxJava等异步处理示例 |
| c4-network-lib | 网络操作 | 包含HTTP客户端、网络错误处理等示例 |
| c5-file-lib | 文件操作 | 包含文件读写、序列化等操作示例 |
| c6-cache-lib | 缓存操作 | 包含Guava Cache、Caffeine等缓存框架示例 |
| c7-message-lib | 消息传递 | 包含消息队列、发布/订阅模式等示例 |
| c8-config-ops-lib | 配置操作 | 包含属性文件、YAML、JSON配置的处理示例 |
| c9-testing-lib | 测试工具 | 包含JUnit、Mockito等测试框架的使用示例 |
| c10-utils-lib | 通用工具 | 包含字符串处理、日期时间处理等通用工具函数 |
| c11-jvm-lib | JVM特性 | 包含不同JDK版本的特性示例 |

## 模块详情

### c1-orm-lib

ORM框架集成模块，包含以下子模块：
- tk-mybatis-sample：TK MyBatis的使用示例
- mybatis-plus-sample：MyBatis-Plus的使用示例
- jpa-sample：JPA的使用示例
- tk-mybatis-plus-combined-sample：TK MyBatis和MyBatis-Plus的结合使用示例
- mp-shardingjdbc4-sample：MyBatis-Plus和ShardingJDBC 4的集成示例
- mp-shardingjdbc5-sample：MyBatis-Plus和ShardingJDBC 5的集成示例
- mp-clickhouse-sample：MyBatis-Plus和ClickHouse的集成示例

### c2-auth-lib

认证授权模块，包含：
- jwt-login-sample：基于JWT的登录认证实现

### c3-async-lib

异步处理模块，包含：
- CompletableFuture示例
- RxJava示例
- 线程池示例
- 并行流示例

### c4-network-lib

网络操作模块，包含：
- 标准HttpURLConnection示例
- OkHttp示例
- Spring WebClient示例
- 网络错误处理和重试机制示例

### c5-file-lib

文件操作模块，包含：
- 基本文件读写操作
- 文件复制和移动
- 目录操作
- 文件属性操作
- 序列化和反序列化

### c6-cache-lib

缓存操作模块，包含：
- 简单内存缓存示例
- Guava Cache示例
- Caffeine Cache示例
- 缓存过期和刷新示例
- 缓存加载器示例

### c7-message-lib

消息传递模块，包含：
- 简单消息队列示例
- 发布/订阅模式示例
- 消息确认机制示例
- 消息持久化示例

### c8-config-ops-lib

配置操作模块，包含：
- 属性文件操作
- YAML配置文件操作
- JSON配置文件操作
- 配置验证
- 动态配置加载

### c9-testing-lib

测试工具模块，包含：
- JUnit 5测试示例
- Mockito模拟测试示例
- AssertJ断言示例
- 测试数据生成器

### c10-utils-lib

通用工具模块，包含：
- 字符串工具类
- 日期时间工具类
- 集合工具类
- 数学工具类
- 数组工具类

### c11-jvm-lib

JVM特性模块，包含：
- jdk8-sample：Java 8特性示例（Lambda表达式、Stream API、Optional等）
- jdk17-sample：Java 17特性示例（文本块、密封类、记录类等）
- jdk21-sample：Java 21特性示例（虚拟线程、String模板等）

## 使用方法

1. 克隆项目：
   ```bash
   git clone https://github.com/yourusername/dev-lite.git
   ```

2. 构建项目：
   ```bash
   mvn clean install
   ```

3. 运行各个模块的示例：
   ```bash
   cd c1-orm-lib/tk-mybatis-sample
   mvn exec:java -Dexec.mainClass="com.cv.Main"
   ```

## 技术栈

- Java 17+
- Maven
- Spring Boot
- MyBatis
- JPA
- ShardingJDBC
- JWT
- RxJava
- OkHttp
- Guava
- Caffeine
- JUnit 5
- Mockito
- AssertJ

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 许可证

MIT License


