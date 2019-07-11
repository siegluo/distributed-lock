package cn.roger.distributed.lock.zookeeper;

import cn.roger.distributed.lock.api.manager.AbstractLockManager;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;


public class ZookeeperLockManager extends AbstractLockManager implements DisposableBean {

    private final CuratorFramework client;

    private AsyncTaskExecutor mutexTaskExecutor = new ThreadPoolTaskExecutor();

    private boolean mutexTaskExecutorExplicitlySet;

    {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) this.mutexTaskExecutor;
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setBeanName("ZookeeperLockRegistryExecutor");
        threadPoolTaskExecutor.initialize();
    }

    public ZookeeperLockManager(CuratorFramework client) {
        if (null == client) {
            throw new NullPointerException();
        }
        this.client = client;
    }

    public void setMutexTaskExecutor(AsyncTaskExecutor mutexTaskExecutor) {
        Assert.notNull(mutexTaskExecutor, "'mutexTaskExecutor' cannot be null");
        ((ExecutorConfigurationSupport) this.mutexTaskExecutor).shutdown();
        this.mutexTaskExecutor = mutexTaskExecutor;
        this.mutexTaskExecutorExplicitlySet = true;
    }

    @Override
    public void destroy() throws Exception {
        if (!this.mutexTaskExecutorExplicitlySet) {
            ((ExecutorConfigurationSupport) this.mutexTaskExecutor).shutdown();
        }
    }

    @Override
    protected Lock createLock(String lockName) {
        return new ZkLock(this.client, this.mutexTaskExecutor, lockName);
    }

    @Override
    protected ReadWriteLock createReadWriteLock(String lockName) {
        return new ZkReadWriteLock(this.client, this.mutexTaskExecutor, lockName);
    }
}
