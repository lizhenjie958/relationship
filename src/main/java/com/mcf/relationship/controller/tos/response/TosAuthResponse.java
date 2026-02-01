package com.mcf.relationship.controller.tos.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class TosAuthResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -8087606514927144691L;
    private String accessKey;
    private String accessSecret;
    private String securityToken;
    private String endpoint;
    private String bucket;
    private String region;
    private String dir;
}
