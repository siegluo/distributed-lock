package cn.roger.distributed.lock.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LockAspect {

    private DisLockInterceptor disLockInterceptor;

    /**
     * 对DisLock注解进行aop，对切点加锁
     *
     * @param
     * @return
     * @author Roger
     * @date 18-1-16 上午10:43
     */
    @Pointcut("@annotation(cn.roger.distributed.lock.api.DisLock)")
    public void lockPoint() {

    }

    /**
     * 环绕通知
     *
     * @param pjp
     * @return Object
     * @author Roger
     * @date 18-1-16 上午10:43
     */
    @Around("lockPoint()")
    public Object interceptorDisLockMethod(ProceedingJoinPoint pjp) throws Throwable {
        return disLockInterceptor.interceptDisLockMethod(pjp);
    }

    public void setDisLockInterceptor(DisLockInterceptor disLockInterceptor) {
        this.disLockInterceptor = disLockInterceptor;
    }
}
