package cn.roger.distributed.lock.spring;

import cn.roger.distributed.lock.api.manager.LockManager;
import cn.roger.distributed.lock.redis.manager.RedissonLockManager;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({RedissonClient.class, RedissonLockManager.class})
@ConditionalOnBean(RedissonClient.class)
public class RedisLockManagerAutoConfiguration {
    @Bean
    public LockManager lockManager(RedissonClient redissonClient) {
        return new RedissonLockManager(redissonClient);
    }
}
