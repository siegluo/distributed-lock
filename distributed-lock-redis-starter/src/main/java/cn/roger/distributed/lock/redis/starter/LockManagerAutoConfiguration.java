package cn.roger.distributed.lock.redis.starter;

import cn.roger.distributed.lock.api.manager.LockManager;
import cn.roger.distributed.lock.core.manager.AopLockAdvisor;
import cn.roger.distributed.lock.core.manager.SimpleLockManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LockManagerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LockManager.class)
    public LockManager lockManager() {
        return new SimpleLockManager();
    }

    @Bean
    public AopLockAdvisor aopLockAdvisor(LockManager lockManager) {
        return new AopLockAdvisor(lockManager);
    }
}
