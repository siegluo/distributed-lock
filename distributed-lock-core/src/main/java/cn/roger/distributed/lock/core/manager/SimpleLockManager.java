package cn.roger.distributed.lock.core.manager;

import cn.roger.distributed.lock.api.manager.AbstractLockManager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class SimpleLockManager extends AbstractLockManager {
    @Override
    protected Lock createLock(String lockName) {
        return new ReentrantLock();
    }

    @Override
    protected ReadWriteLock createReadWriteLock(String lockName) {
        return new ReentrantReadWriteLock();
    }
}
