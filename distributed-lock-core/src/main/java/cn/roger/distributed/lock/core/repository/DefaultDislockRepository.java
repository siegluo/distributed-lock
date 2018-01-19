package cn.roger.distributed.lock.core.repository;

import org.springframework.integration.support.locks.LockRegistry;

import java.util.concurrent.locks.Lock;

public class DefaultDislockRepository implements DisLockRepository {

    private LockRegistry lockRegistry;

    /**
     * 获得锁
     *
     * @param path path
     * @return 获得锁
     */
    @Override
    public Lock obtain(String path) {
        return lockRegistry.obtain(path);
    }

    /**
     * 释放锁
     *
     * @param lock 事务
     * @return 释放锁
     */
    @Override
    public void unlock(Lock lock) {
        lock.unlock();
    }

    @Override
    public void setLockRegistry(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }
}
