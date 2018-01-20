package cn.roger.distributed.lock.core.factory;

import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public final class FactoryBuilder {

    private static List<BeanFactory> beanFactorys = new ArrayList<>();

    private static ConcurrentMap<Class, SingletonFactory> beanConcurrentMap = new ConcurrentHashMap<>();

    /**
     * 注册BeanFactory
     *
     * @author Roger
     * @date 18-1-20 上午11:41
     */
    public static void regisiterBeanFactory(BeanFactory beanFactory) {
        beanFactorys.add(beanFactory);
    }

    /**
     * 获得单例工厂
     *
     * @author Roger
     * @date 18-1-20 上午11:42
     */
    public static <T> SingletonFactory<T> factoryOf(Class<T> clazz) {
        if (beanConcurrentMap.get(clazz) == null) {
            String clazzName = clazz.getName();
            for (BeanFactory beanFactory : beanFactorys) {
                if (beanFactory.isFactory(clazz)) {
                    beanConcurrentMap.putIfAbsent(clazz, new SingletonFactory<T>(clazzName, beanFactory.getBean(clazz)));
                }
            }
            if (beanConcurrentMap.get(clazz) == null) {
                beanConcurrentMap.putIfAbsent(clazz, new SingletonFactory<T>(clazzName));
            }
        }
        return beanConcurrentMap.get(clazz);
    }

    public static class SingletonFactory<T> {

        private volatile T instance = null;

        private String className;

        public SingletonFactory(String className, T instance) {
            this.instance = instance;
            this.className = className;
        }

        public SingletonFactory(String className) {
            this.className = className;
        }

        /**
         * 获得单例，优先beanfactory中获得
         *
         * @author Roger
         * @date 18-1-20 上午11:42
         */
        public T getInstance() {
            if (instance == null) {
                synchronized (SingletonFactory.class) {
                    if (instance == null) {
                        try {
                            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                            Class<?> clazz = classLoader.loadClass(className);
                            instance = (T) clazz.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return instance;
        }
    }
}
