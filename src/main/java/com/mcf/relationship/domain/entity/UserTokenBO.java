package com.mcf.relationship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhuPo
 * @date 2026/1/30 17:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8437642959473868731L;
    private Date loginTime;
    private Long userId;
    private String openId;
}
