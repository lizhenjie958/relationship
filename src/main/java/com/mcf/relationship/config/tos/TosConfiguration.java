package com.mcf.relationship.config.tos;

import com.volcengine.tos.TOSV2;
import com.volcengine.tos.TOSV2ClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2025/11/20 14:26
 */
@Configuration
@EnableConfigurationProperties(TosProperties.class)
public class TosConfiguration {

    @Resource
    private TosProperties tosProperties;

    @Bean("tosClient")
    public TOSV2 tosClient() {
        return new TOSV2ClientBuilder()
                .build(tosProperties.getRegion(), tosProperties.getEndpoint(), tosProperties.getAccessKey(), tosProperties.getSecretKey());
    }

}
