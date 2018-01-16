package cn.roger.distributed.lock.core.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 */
public class ReflectionUtils {


    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static Class getDeclaringType(Class aClass, String methodName, Class<?>[] parameterTypes) {
        Method method;
        Class findClass = aClass;
        do {
            Class[] clazzes = findClass.getInterfaces();
            for (Class clazz : clazzes) {
                try {
                    method = clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (NoSuchMethodException e) {
                    method = null;
                }
                if (method != null) {
                    return clazz;
                }
            }
            findClass = findClass.getSuperclass();
        } while (!findClass.equals(Object.class));
        return aClass;
    }

    public static Object getNullValue(Class type) {
        // 处理基本类型
        if (boolean.class.equals(type)) {
            return false;
        } else if (byte.class.equals(type)) {
            return 0;
        } else if (short.class.equals(type)) {
            return 0;
        } else if (int.class.equals(type)) {
            return 0;
        } else if (long.class.equals(type)) {
            return 0;
        } else if (float.class.equals(type)) {
            return 0;
        } else if (double.class.equals(type)) {
            return 0;
        }
        // 处理对象
        return null;
    }
}
