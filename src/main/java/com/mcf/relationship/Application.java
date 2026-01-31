package com.mcf.relationship;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author ZhuPo
 * @date 2026/1/24 18:48
 */
@SpringBootApplication
@MapperScan("com.mcf.relationship.infra.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
