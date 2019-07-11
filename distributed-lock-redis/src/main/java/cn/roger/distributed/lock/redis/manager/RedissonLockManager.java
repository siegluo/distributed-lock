package cn.roger.distributed.lock.redis.manager;

import cn.roger.distributed.lock.api.manager.AbstractLockManager;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;


public class RedissonLockManager extends AbstractLockManager {
    private RedissonClient redisson;

    public RedissonLockManager(RedissonClient redisson) {
        if (null == redisson) {
            throw new NullPointerException();
        }
        this.redisson = redisson;
    }

    @Override
    protected Lock createLock(String lockName) {
        return redisson.getFairLock(lockName);
    }

    @Override
    protected ReadWriteLock createReadWriteLock(String lockName) {
        return redisson.getReadWriteLock(lockName);
    }
}
