package cn.roger.distributed.lock.core.manager;

public interface DisLockConfig {
    /**
     * @author Roger
     * @date 18-1-20 上午11:48
     */
    long getWaitLockTime();

    /**
     * @author Roger
     * @date 18-1-20
     */
    void setWaitLockTime(long waitLockTime);
}
