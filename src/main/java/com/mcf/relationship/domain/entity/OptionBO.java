package com.mcf.relationship.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ZhuPo
 * @date 2026/2/2 12:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4918605846099569394L;
    private String key;
    private String value;
}
