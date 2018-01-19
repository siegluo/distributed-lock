package cn.roger.distributed.lock.core.manager;

import cn.roger.distributed.lock.core.repository.DisLockRepository;

import java.util.concurrent.locks.Lock;

public class DisLockManager {

    private DisLockRepository disLockRepository;

    private long waitLockTime;

    public Lock getLock(String path) {

        return disLockRepository.obtain(path);
    }

    public void unlock(Lock lock) {
        lock.unlock();
    }

    public void setDisLockRepository(DisLockRepository disLockRepository) {
        this.disLockRepository = disLockRepository;
    }
}
