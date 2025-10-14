package com.cv.lite;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: xutu
 * @since: 2025/9/26 13:22
 * <p>
 * 在主启动类或者配置类上加
 *
 */
@SpringBootApplication
public class JwtLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtLoginApplication.class, args);
    }
}
