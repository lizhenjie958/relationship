package com.mcf.relationship.advice.aspect;

import com.mcf.relationship.common.annotations.DistributedTask;
import com.mcf.relationship.infra.manager.RedissonManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZhuPo
 * @date 2026/2/5 15:53
 */
@Aspect
@Component
@Slf4j
public class DistributedTaskAspect {
    @Resource
    private RedissonManager redissonManager;

    @Around("@annotation(distributedTask)")
    public Object handleDistributedTask(ProceedingJoinPoint joinPoint,
                                        DistributedTask distributedTask) throws Throwable {
        String taskName = distributedTask.name();

        // 尝试获取锁
        boolean locked = redissonManager.tryLock(taskName,
                distributedTask.waitTime(), distributedTask.holdTime());

        if (!locked) {
            return null;
        }

        try {
            return joinPoint.proceed();
        } finally {
            redissonManager.unlock(taskName);
        }
    }
}
