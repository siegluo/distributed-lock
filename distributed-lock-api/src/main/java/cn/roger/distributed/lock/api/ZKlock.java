package cn.roger.distributed.lock.api;

import org.apache.zookeeper.Watcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZKlock implements Lock {

    private final ExecutorService executorService;

    private final Watcher watcher;

    public ZKlock(ExecutorService executorService, Watcher watcher) {
        this.executorService = executorService;
        this.watcher = watcher;
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {


        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
