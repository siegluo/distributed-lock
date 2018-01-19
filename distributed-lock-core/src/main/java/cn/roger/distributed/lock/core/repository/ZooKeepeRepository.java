package cn.roger.distributed.lock.core.repository;

import cn.roger.distributed.lock.api.ZKlock;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.locks.Lock;

public class ZooKeepeRepository implements DisLockRepository {


    @Override
    public Lock create(String path) {

        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        };


        return new ZKlock();
    }

    @Override
    public Lock create(String path, String s) {
        return null;
    }

    @Override
    public int update(String path) {
        return 0;
    }

    @Override
    public int delete(String path) {
        return 0;
    }

}
