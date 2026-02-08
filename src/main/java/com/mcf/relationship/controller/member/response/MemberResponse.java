package com.mcf.relationship.controller.member.response;

import com.mcf.relationship.common.protocol.BaseResponse;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/2/8 09:43
 */
@Data
public class MemberResponse extends BaseResponse {
    @Serial
    private static final long serialVersionUID = 7964075198830124630L;
    /**
     * 会员生效时间
     */
    private LocalDate enableTime;

    /**
     * 会员过期时间
     */
    private LocalDate expireTime;

    /**
     * 生效状态
     */
    private Integer enableStatus;
}
