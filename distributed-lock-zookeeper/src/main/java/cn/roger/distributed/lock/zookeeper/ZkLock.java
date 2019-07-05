package cn.roger.distributed.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZkLock implements Lock {
    private final CuratorFramework client;

    private final InterProcessMutex mutex;

    private final AsyncTaskExecutor mutexTaskExecutor;

    private final String path;


    public ZkLock(CuratorFramework client, AsyncTaskExecutor mutexTaskExecutor, String path) {
        this.client = client;
        this.mutex = new InterProcessMutex(client, path);
        this.mutexTaskExecutor = mutexTaskExecutor;
        this.path = path;
    }

    public ZkLock(CuratorFramework client, InterProcessMutex mutex, AsyncTaskExecutor mutexTaskExecutor, String path) {
        this.client = client;
        this.mutex = mutex;
        this.mutexTaskExecutor = mutexTaskExecutor;
        this.path = path;
    }

    @Override
    public void lock() {
        try {
            this.mutex.acquire();
        } catch (Exception e) {
            throw new RuntimeException("Failed to acquire mutex at " + this.path, e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        boolean locked = false;
        while (!locked) {
            locked = tryLock(1, TimeUnit.SECONDS);
        }

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        Future<String> future = null;
        try {
            long startTime = System.currentTimeMillis();

            future = this.mutexTaskExecutor.submit(new Callable<String>() {

                @Override
                public String call() throws Exception {
                    return client.create()
                            .creatingParentContainersIfNeeded()
                            .withProtection()
                            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                            .forPath(path);
                }

            });

            long waitTime = unit.toMillis(time);

            String ourPath = future.get(waitTime, TimeUnit.MILLISECONDS);

            if (ourPath == null) {
                future.cancel(true);
                return false;
            } else {
                waitTime = waitTime - (System.currentTimeMillis() - startTime);
                return this.mutex.acquire(waitTime, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException e) {
            future.cancel(true);
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to acquire mutex at " + this.path, e);
        }
    }

    @Override
    public void unlock() {
        try {
            this.mutex.release();
        } catch (Exception e) {
            throw new RuntimeException("Failed to release mutex at " + this.path, e);
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Conditions are not supported");
    }

    public boolean isAcquiredInThisProcess() {
        return this.mutex.isAcquiredInThisProcess();
    }

}
