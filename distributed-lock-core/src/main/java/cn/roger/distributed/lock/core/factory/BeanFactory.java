package cn.roger.distributed.lock.core.factory;

public interface BeanFactory {

    /**
     * 获得bean
     *
     * @param var1
     * @return T
     * @author Roger
     * @date 18-1-20 上午11:16
     */
    <T> T getBean(Class<T> var1);

    /**
     * 判断是否是clazz的工厂类
     *
     * @param clazz
     * @return boolean
     * @author Roger
     * @date 18-1-20 上午11:17
     */
    <T> boolean isFactory(Class<T> clazz);

}
