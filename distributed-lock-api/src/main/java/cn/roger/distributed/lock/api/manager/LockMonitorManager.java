package cn.roger.distributed.lock.api.manager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 锁监控工厂,用于对锁信息进行监控
 */
public interface LockMonitorManager {

    LockMonitor getLockMonitor();

}
