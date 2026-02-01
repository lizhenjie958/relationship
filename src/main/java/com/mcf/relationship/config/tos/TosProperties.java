package com.mcf.relationship.config.tos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZhuPo
 * @date 2025/11/20 14:28
 */
@ConfigurationProperties(prefix = "tos")
@Data
public class TosProperties {
    /**
     * 区域
     */
    private String region;

    /**
     * 访问密钥ID
     */
    private String accessKey;

    /**
     * 访问密钥
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucket;

    /**
     * 端点地址
     */
    private String endpoint;

    /**
     * 临时凭证过期时间（秒）
     */
    private Long expireSeconds = 3600L;

    /**
     * 上传文件大小限制（MB）
     */
    private Long maxFileSize = 100L;
    /**
     * 存储路径
     */
    private String basePath;
}
