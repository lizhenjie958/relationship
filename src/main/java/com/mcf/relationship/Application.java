package com.mcf.relationship;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author ZhuPo
 * @date 2026/1/24 18:48
 */
@SpringBootApplication
@MapperScan("com.mcf.relationship.infra.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
