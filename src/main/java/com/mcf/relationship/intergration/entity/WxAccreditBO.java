package com.mcf.relationship.intergration.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/2/4 21:08
 */
@Data
public class WxAccreditBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3017978413091687069L;
    @JSONField(name = "openid")
    private String openId;
    @JSONField(name = "session_key")
    private String sessionKey;
}
