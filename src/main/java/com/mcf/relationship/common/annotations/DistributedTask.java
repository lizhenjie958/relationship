package com.mcf.relationship.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author ZhuPo
 * @date 2026/2/5 12:46
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedTask {
    String name();          // 任务名称
    long waitTime() default 0;      // 等待时间(秒)
    long holdTime() default 60;     // 持有时间(秒)
}
