package cn.roger.distributed.lock.core.utils;

import cn.roger.distributed.lock.api.DisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public class DisLockUtils {

    public static Method getDisLockMethod(ProceedingJoinPoint pjp) {
        //代理方法
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        if (method.getAnnotation(DisLock.class) == null) {
            //实际方法
            try {
                method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }
        return method;
    }
}
