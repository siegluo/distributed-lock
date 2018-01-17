package cn.roger.distributed.lock.core.interceptor;

import cn.roger.distributed.lock.api.DisLock;
import cn.roger.distributed.lock.api.LockTypeEnum;
import cn.roger.distributed.lock.core.utils.DisLockUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.integration.support.locks.LockRegistry;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class DisLockInterceptor {

    static final Logger logger = Logger.getLogger(DisLockInterceptor.class.getSimpleName());

    //TODO
//    private DisLockManager disLockManager;

    private LockRegistry lockRegistry;

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

    public Object methodProceed(ProceedingJoinPoint pjp) throws Throwable {

        String methodName = DisLockUtils.getDisLockMethod(pjp).getName();
        String className = pjp.getTarget().getClass().getName();
        //TODO
        String path = "/root" + "/" + className + "/" + methodName;
        lockAndExec(path, 1000, pjp);
        return pjp.proceed();
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

    public Object lockAndExec(String lockKey, long waitLockTime, ProceedingJoinPoint pjp) {
        logger.info("加锁");
        Lock lock = lockRegistry.obtain(lockKey);
        try {
            if (!lock.tryLock(waitLockTime, TimeUnit.MILLISECONDS)) {
                logger.info("加锁失败，任务中止");
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
            lock.unlock();
        }
        return null;
    }

    public void setLockRegistry(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }
}
