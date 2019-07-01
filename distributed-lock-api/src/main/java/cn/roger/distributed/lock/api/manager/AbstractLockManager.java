package cn.roger.distributed.lock.api.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;


public abstract class AbstractLockManager implements LockManager {
    private final Map<String, Lock> lockStore          = new HashMap<>(128);
    private final Map<String, ReadWriteLock> readWriteLockStore = new HashMap<>(128);

    @Override
    public Lock getLock(String lockName) {
        Lock lock = lockStore.get(lockName);
        if (lock != null) {
            return lock;
        }
        synchronized (lockStore) {
            return lockStore.computeIfAbsent(lockName, this::createLock);
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock(String lockName) {
        ReadWriteLock lock = readWriteLockStore.get(lockName);
        if (lock != null) {
            return lock;
        }
        synchronized (readWriteLockStore) {
            return readWriteLockStore.computeIfAbsent(lockName, this::createReadWriteLock);
        }
    }

    protected abstract Lock createLock(String lockName);

    protected abstract ReadWriteLock createReadWriteLock(String lockName);

}
