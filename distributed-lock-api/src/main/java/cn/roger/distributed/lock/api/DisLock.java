package cn.roger.distributed.lock.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DisLock {

    /**
     * 获得锁的方式
     */
    LockTypeEnum type() default LockTypeEnum.METHOD;

    /**
     * type = LockTypeEnum.PARAMETER 时才会使用, 通过全类名，方法名与参数位置对应的参数的hash值获得锁，默认第一个参数
     */
    int value() default 0;

    /**
     * 获得锁的方法
     * 若type = LockTypeEnum.CUSTOM 时需要指明，需要在同一个类中声明自定义获得锁的方法名, 返回值必须为 String
     * 若type = LockTypeEnum.REQUEST 时,可不传，若传值，需要在filter中先自定义方法，然后传入该方法名
     */
    String lockMethod() default "";

    /**
     * 获得锁失败时需要执行的方法， 需要在同一个类中声明自定义获得锁的方法名, 无返回值, 可不传
     */
    String failMethod() default "";
}
