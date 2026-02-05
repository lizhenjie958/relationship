package com.mcf.relationship.config.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

/**
 * 不想删除这些代码，有不上使用它，暂时以配置类保留
 * @Author ZhuPo
 * @date 2026/1/31 13:35
 */
@Configuration
@Profile("other")
@Deprecated
public class XMemcachedConfig {

    @Value("${memcached.servers}")
    private String servers;

    @Value("${memcached.pool-size:10}")
    private int poolSize;

    @Value("${memcached.op-timeout:3000}")
    private int opTimeout;

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(servers)
        );

        // 配置连接池
        builder.setConnectionPoolSize(poolSize);

        // 配置协议
        builder.setCommandFactory(new BinaryCommandFactory());

        // 配置序列化器
        builder.setTranscoder(new SerializingTranscoder());

        // 配置失败模式
        builder.setFailureMode(false);

        // 配置超时
        builder.setOpTimeout(opTimeout);

        // 启用心跳检测
        builder.getConfiguration().setSessionIdleTimeout(5000);

        MemcachedClient client = builder.build();

        // 设置主备模式（可选）
        client.setPrimitiveAsString(true);

        return client;
    }
}
