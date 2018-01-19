package cn.roger.distributed.lock.core.manager;

import cn.roger.distributed.lock.core.repository.DisLockRepository;
import cn.roger.distributed.lock.core.utils.DisLockUtils;
import org.springframework.integration.support.locks.LockRegistry;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;

public class DisLockManager {

    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

    private static volatile Map<String, Deque> LOCKMAP = new HashMap<>();

    private DisLockRepository disLockRepository;

    private LockRegistry lockRegistry;

    public Lock getLock(String path, boolean fair) {

        if (!isRegister(path)) {
            register(path);
        }

        if (fair) {
            String s = DisLockUtils.getDateString();
            CURRENT.set(s);
            LOCKMAP.get(path).push(s);
            return disLockRepository.create(path, s);
        } else {
            return disLockRepository.create(path);
        }
    }

    public void unLock() {

    }

    public void register(String path) {
        synchronized (this) {
            Deque<String> deque = new ConcurrentLinkedDeque<>();
            LOCKMAP.put(path, deque);
        }
    }

    protected boolean isRegister(String path) {
        return LOCKMAP.get(path) != null;
    }

    public void setDisLockRepository(DisLockRepository disLockRepository) {
        this.disLockRepository = disLockRepository;
    }
}
