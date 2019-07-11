package cn.roger.distributed.lock.zookeeper.starter;

import cn.roger.distributed.lock.api.manager.LockManager;
import cn.roger.distributed.lock.zookeeper.ZookeeperLockManager;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({CuratorFramework.class, ZookeeperLockProperties.class, ZookeeperLockManager.class})
@ConditionalOnBean(CuratorFramework.class)
@EnableConfigurationProperties(ZookeeperLockProperties.class)
public class ZookeeperLockManagerAutoConfiguration {
    @Autowired
    ZookeeperLockProperties properties;

    @Bean
    public LockManager lockManager(CuratorFramework client) {
        return new ZookeeperLockManager(client);
    }

    @Bean
    public CuratorFramework curatorFramework() {
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(properties.getZookeeperClientConfig().getUrls())
                .sessionTimeoutMs(properties.getZookeeperClientConfig().getSessionTimeout())
                .retryPolicy(retryPolicy)
                .build();
        //3 开启连接
        cf.start();
        return cf;
    }
}
