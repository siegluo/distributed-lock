package cn.roger.distributed.lock.spring;

import cn.roger.distributed.lock.core.factory.BeanFactory;
import cn.roger.distributed.lock.core.factory.FactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class SpringPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 在spring容器加载完成后调用该方法，将springbeanfactory注册到factorybuilder中
     *
     * @param contextRefreshedEvent
     * @return void
     * @author Roger
     * @date 18-1-20 上午11:18
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        //防止spring的子容器加载后，重复注册
        if (applicationContext.getParent() == null) {
            FactoryBuilder.regisiterBeanFactory(applicationContext.getBean(BeanFactory.class));
        }
    }

}
