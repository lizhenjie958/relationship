package com.mcf.relationship.exception;

/**
 * @Author ZhuPo
 * @date 2026/1/24 19:54
 */
public interface ExceptionInterface {
    /**
     * @return 响应码
     */
    Integer getCode();

    /**
     * @return 响应信息
     */
    String getMsg();
}
