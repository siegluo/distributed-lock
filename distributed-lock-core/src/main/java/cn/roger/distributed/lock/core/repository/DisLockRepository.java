package cn.roger.distributed.lock.core.repository;

import org.springframework.integration.support.locks.LockRegistry;

import java.util.concurrent.locks.Lock;

public interface DisLockRepository {

    /**
     * 获得锁
     *
     * @param path path
     * @return 获得锁
     */
    Lock obtain(String path);

    /**
     * 释放锁
     *
     * @param lock 事务
     * @return 释放锁
     */
    void unlock(Lock lock);


    void setLockRegistry(LockRegistry lockRegistry);
}
