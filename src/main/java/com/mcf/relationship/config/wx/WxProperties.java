package com.mcf.relationship.config.wx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/2/4 21:02
 */
@ConfigurationProperties(prefix = "wx")
@Data
public class WxProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 5082239038259690941L;
    /**
     * 微信appId
     */
    private String appId;
    /**
     * 微信appSecret
     */
    private String appSecret;
}
