package cn.roger.distributed.lock.redis.starter;

import cn.roger.distributed.lock.api.manager.LockManager;
import cn.roger.distributed.lock.redis.manager.RedissonLockManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({RedissonClient.class, RedisLockProperties.class, RedissonLockManager.class})
@ConditionalOnBean(RedissonClient.class)
@EnableConfigurationProperties(RedisLockProperties.class)
public class RedisLockManagerAutoConfiguration {
    @Autowired
    RedisLockProperties properties;

    @Bean
    public LockManager lockManager(RedissonClient redissonClient) {
        return new RedissonLockManager(redissonClient);
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        switch (properties.getType()) {
            case "single":
                copy(config.useSingleServer(), properties.getSingleServerConfig());
                break;
            case "cluster":
                copy(config.useClusterServers(), properties.getClusterServersConfig());
                break;
            case "master slave":
                copy(config.useMasterSlaveServers(), properties.getMasterSlaveServersConfig());
                break;
            case "replicated":
                copy(config.useReplicatedServers(), properties.getReplicatedServersConfig());
                break;
            default:
                copy(config.useSingleServer(), properties.getSingleServerConfig());
        }
        return Redisson.create(config);
    }

    private void copy(ReplicatedServersConfig useReplicatedServers, ReplicatedServersConfig replicatedServersConfig) {
        useReplicatedServers = replicatedServersConfig;
    }

    private void copy(MasterSlaveServersConfig useMasterSlaveServers, MasterSlaveServersConfig masterSlaveServersConfig) {
        useMasterSlaveServers = masterSlaveServersConfig;
    }

    private void copy(ClusterServersConfig useClusterServers, ClusterServersConfig clusterServersConfig) {
        useClusterServers = clusterServersConfig;
    }

    private void copy(SingleServerConfig useSingleServer, SingleServerConfig singleServerConfig) {
        useSingleServer = singleServerConfig;
    }
}
