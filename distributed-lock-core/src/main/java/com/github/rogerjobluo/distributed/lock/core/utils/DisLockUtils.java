package com.github.rogerjobluo.distributed.lock.core.utils;

import com.github.rogerjobluo.distributed.lock.api.DisLock;
import com.github.rogerjobluo.distributed.lock.api.MethodTypeEnum;
import com.github.rogerjobluo.distributed.lock.core.factory.FactoryBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Random;

public class DisLockUtils {

    /**
     * 获得Method
     *
     * @param pjp
     * @return Method
     * @author Roger
     * @date 18-1-20 上午11:29
     */
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

    /**
     * 生成基于时间的随机码
     *
     * @author Roger
     * @date 18-1-20 上午11:29
     */
    public static String getDateString() {

        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 8) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return String.valueOf(System.currentTimeMillis()) + pwd;
    }

    /**
     * @author Roger
     * @date 18-1-20 上午11:29
     */
    public static void invok(ProceedingJoinPoint pjp, MethodTypeEnum methodTypeEnum) {
        Method disLockMethod = DisLockUtils.getDisLockMethod(pjp);
        DisLock disLock = disLockMethod.getAnnotation(DisLock.class);
        String methodName = null;
        switch (methodTypeEnum) {
            case FAILMETHOD:
                methodName = disLock.failMethod();
                break;
            case LOCKMETHOD:
                methodName = disLock.lockMethod();
                break;
            case FINSHEDMETHOD:
                methodName = disLock.finshedMethod();
                break;
            default:
        }
        if (StringUtils.hasText(methodName)) {
            try {
                Method method = pjp.getTarget().getClass().getMethod(methodName, disLockMethod.getParameterTypes());
                method.invoke(FactoryBuilder.factoryOf(pjp.getTarget().getClass()).getInstance(), pjp.getArgs());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}
