package com.github.rogerjobluo.distributed.lock.core.interceptor;

import com.github.rogerjobluo.distributed.lock.api.DisLock;
import com.github.rogerjobluo.distributed.lock.api.LockTypeEnum;
import com.github.rogerjobluo.distributed.lock.api.MethodTypeEnum;
import com.github.rogerjobluo.distributed.lock.core.manager.DisLockConfigurater;
import com.github.rogerjobluo.distributed.lock.core.manager.DisLockManager;
import com.github.rogerjobluo.distributed.lock.core.utils.DisLockUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class DisLockInterceptor {

    static final Logger logger = Logger.getLogger(DisLockInterceptor.class.getSimpleName());

    private DisLockConfigurater disLockConfigurater;

    /**
     * 拦截器拦截到注解后，主要执行的方法
     *
     * @author Roger
     * @date 18-1-20 上午11:45
     */
    public Object interceptDisLockMethod(ProceedingJoinPoint pjp) throws Throwable {

        //获得注解的type
        Method disLockMethod = DisLockUtils.getDisLockMethod(pjp);
        DisLock disLock = disLockMethod.getAnnotation(DisLock.class);
        LockTypeEnum type = disLock.type();

        switch (type) {

            case METHOD:
                return methodProceed(pjp);
            case PARAMETER:
                return parameterProceed(pjp);
            case REQUEST:
                return requestProceed(pjp);
            case CUSTOM:
                return customProceed(pjp);
            default:
                return pjp.proceed();
        }
    }

    /**
     * 为方法级别的锁时
     *
     * @author Roger
     * @date 18-1-20 上午11:46
     */
    public Object methodProceed(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = DisLockUtils.getDisLockMethod(pjp).getName();
        String className = pjp.getTarget().getClass().getName();
        String path = className + methodName;
        long waitLockTime = disLockConfigurater.getDisLockConfig().getWaitLockTime();

        return lockAndExec(path, waitLockTime, pjp);
    }

    public Object parameterProceed(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    public Object requestProceed(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    public Object customProceed(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

    /**
     * 获得锁，释放锁，目前使用的是spring-integration中的zk锁
     *
     * @author Roger
     * @date 18-1-20 上午11:46
     */
    public Object lockAndExec(String lockKey, long waitLockTime, ProceedingJoinPoint pjp) {
        logger.info("加锁");
        DisLockManager disLockManager = disLockConfigurater.getDisLockManager();
        Lock lock = disLockManager.getLock(lockKey);
        try {
            if (!lock.tryLock(waitLockTime, TimeUnit.MILLISECONDS)) {
                logger.info("加锁失败，任务中止");
                DisLockUtils.invoke(pjp, MethodTypeEnum.FAILMETHOD);
                return null;
            }
        } catch (InterruptedException e) {
            logger.warning("在等待加锁时被中止:" + e.getMessage());
            return null;
        }
        // 已经加锁OK，执行
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            logger.warning("执行任务时出现异常，将终止:" + e.getMessage());
        } finally {
            disLockManager.unlock(lock);
            DisLockUtils.invoke(pjp, MethodTypeEnum.FINISHEDMETHOD);
        }
        return null;
    }

    public void setDisLockConfigurater(DisLockConfigurater disLockConfigurater) {
        this.disLockConfigurater = disLockConfigurater;
    }
}
