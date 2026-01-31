package com.mcf.relationship.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.util.Collections;

import static com.baomidou.mybatisplus.generator.config.rules.DateType.TIME_PACK;

public class MybatisPlus {
    private static final String PATH = "jdbc:mysql://rm-bp1843l8qlcjejn2aio.mysql.rds.aliyuncs.com/config?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai";

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/src/main/java";
        FastAutoGenerator.create(MybatisPlus.PATH, "platform", "FYTIUfuytftyu5r4R^")
                .globalConfig(builder -> {
                    builder.outputDir(path).disableOpenDir().dateType(TIME_PACK);
                })
                .packageConfig((scanner, builder) -> {
                    builder.parent("com.mcf.relationship")
                            .entity("infra.model")    // 实体类包
                            .mapper("infra.mapper")  // Mapper包
                            .service("domain.service")    // Service接口包
                            .serviceImpl("domain.service.impl") // Service实现包
                            .controller("controller");
                    builder.pathInfo(Collections.singletonMap(
                            OutputFile.xml,
                            "src/main/resources/mapper"
                    ));
                })

                .dataSourceConfig(builder -> {
                    builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        // 使用新版本API获取列类型信息
                        String dataType = metaInfo.getTypeName().toLowerCase();

                        // 将 tinyint 映射为 Integer
                        if (dataType.contains("tinyint")) {
                            return DbColumnType.INTEGER;
                        }

                        // 其他类型映射
                        if (dataType.contains("datetime") || dataType.contains("timestamp")) {
                            return DbColumnType.LOCAL_DATE_TIME;
                        }

                        // 默认使用注册的类型
                        return typeRegistry.getColumnType(metaInfo);
                    });
                })
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(scanner.apply("请输入表名"))
                            .addTablePrefix("r_")
                            .entityBuilder().enableChainModel().enableLombok().disableSerialVersionUID().formatFileName("%sDO")
                            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp");
                })
                .execute();
    }
}