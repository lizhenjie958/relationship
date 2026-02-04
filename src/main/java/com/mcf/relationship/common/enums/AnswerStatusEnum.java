package com.mcf.relationship.common.enums;

import com.mcf.relationship.common.consts.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhuPo
 * @date 2026/2/4 14:09
 */
@Getter
@AllArgsConstructor
public enum AnswerStatusEnum {
    ANSWERING(1,"答题中"),
    COMPLETED(2,"已完成"),
    GIVEN_UP(3,"已放弃"),
    TIMED_OUT(4,"已超时"),
    ;
    private final Integer status;
    private final String desc;

    /**
     * 获取描述
     * @param status
     * @return
     */
    public static String getDesc(Integer status){
        for (AnswerStatusEnum value : values()) {
            if(value.status.equals(status)){
                return value.desc;
            }
        }
        return CommonConst.UNKNOWN;
    }
}
