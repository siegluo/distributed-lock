package cn.roger.distributed.lock.api.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;


public abstract class AbstractLockManager implements LockManager {

    private static final String DEFAULT_ROOT = "/Distributed-Lock/";
    private final boolean trackingTime = false;

    private final Map<String, Lock> lockStore = new ConcurrentHashMap<>(128);
    private final Map<String, ReadWriteLock> readWriteLockStore = new ConcurrentHashMap<>(128);

    @Override
    public Lock getLock(String lockName) {
        Lock lock = lockStore.get(lockName);
        if (lock != null) {
            return lock;
        }
        return lockStore.computeIfAbsent(lockName, this::createLock);
    }

    @Override
    public ReadWriteLock getReadWriteLock(String lockName) {
        ReadWriteLock lock = readWriteLockStore.get(lockName);
        if (lock != null) {
            return lock;
        }
        return readWriteLockStore.computeIfAbsent(lockName, this::createReadWriteLock);
    }

    protected abstract Lock createLock(String lockName);

    protected abstract ReadWriteLock createReadWriteLock(String lockName);

}
