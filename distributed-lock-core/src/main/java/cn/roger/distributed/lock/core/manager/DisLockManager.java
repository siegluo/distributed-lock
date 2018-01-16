package cn.roger.distributed.lock.core.manager;

import cn.roger.distributed.lock.core.repository.DisLockRepository;
import org.springframework.integration.support.locks.LockRegistry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Lock;

public class DisLockManager {

    Deque<Lock> LOCKQUE = new ArrayDeque<>();

    private DisLockRepository disLockRepository;

    private LockRegistry lockRegistry;

    //TODO 自定义 lock 实现 Lock 接口
    public Lock getLock(String path) {
        return lockRegistry.obtain(path);
    }

    public void unLock() {

    }

    public void regiiter(Lock lock) {
        synchronized (DisLockManager.class) {
            LOCKQUE.add(lock);
        }
    }

    public void setDisLockRepository(DisLockRepository disLockRepository) {
        this.disLockRepository = disLockRepository;
    }
}
