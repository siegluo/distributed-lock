package cn.roger.distributed.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ZkReadWriteLock implements ReadWriteLock {
    private final CuratorFramework client;

    private final InterProcessMutex readMutex;

    private final InterProcessMutex writeMutex;

    private final AsyncTaskExecutor mutexTaskExecutor;

    private final String path;

    ZkReadWriteLock(CuratorFramework client, AsyncTaskExecutor mutexTaskExecutor, String path) {
        this.client = client;
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, path);
        this.readMutex = interProcessReadWriteLock.readLock();
        this.writeMutex = interProcessReadWriteLock.writeLock();
        this.mutexTaskExecutor = mutexTaskExecutor;
        this.path = path;
    }

    @Override
    public Lock readLock() {
        return new ZkLock(client, readMutex, mutexTaskExecutor, path);
    }

    @Override
    public Lock writeLock() {
        return new ZkLock(client, writeMutex, mutexTaskExecutor, path);
    }

}
