package com.mcf.relationship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ZhuPo
 * @date 2026/1/30 17:19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8437642959473868731L;
    private Long loginTime;
    private Long userId;
}
