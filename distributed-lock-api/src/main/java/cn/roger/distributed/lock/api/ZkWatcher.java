package cn.roger.distributed.lock.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZkWatcher implements Watcher {

    /**
     * 成员变量
     */
    private ZooKeeper zk = null;

    // 当前业务线程竞争锁的时候创建的节点路径
    private String selfPath = null;

    // 当前业务线程竞争锁的时候创建节点的前置节点路径
    private String waitPath = null;

    // 确保连接zk成功；只有当收到Watcher的监听事件之后，才执行后续的操作，否则请求阻塞在createConnection()创建ZK连接的方法中9     private CountDownLatch connectSuccessLatch = new CountDownLatch(1);
    // 标识线程是否执行完任务
    private CountDownLatch threadCompleteLatch = null;

    /**
     * ZK的相关配置常量
     */
    private static final String LOCK_ROOT_PATH = "/myDisLocks";

    private static final String LOCK_SUB_PATH = LOCK_ROOT_PATH + "/thread";

    public ZkWatcher(CountDownLatch latch) {
        this.threadCompleteLatch = latch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
