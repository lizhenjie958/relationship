package com.mcf.relationship.controller.tos.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author ZhuPo
 * @date 2026/1/31 20:54
 */
@Data
public class TosRreSignRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -3533083326389537251L;
    private String source;
    private String fileName;
}
