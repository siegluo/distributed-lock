package cn.roger.distributed.lock.core.manager;

public interface DisLockConfig {

    long getWaitLockTime();

    void setWaitLockTime(long waitLockTime);
}
