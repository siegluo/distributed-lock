package com.github.rogerjobluo.distributed.lock.spring;

import com.github.rogerjobluo.distributed.lock.core.manager.DisLockConfig;

public class DefaultDisLockConfig implements DisLockConfig {

    public static final DisLockConfig INSTANCE = new DefaultDisLockConfig();

    private long waitLockTime = 1000;

    @Override
    public long getWaitLockTime() {
        return this.waitLockTime;
    }

    @Override
    public void setWaitLockTime(long waitLockTime) {
        this.waitLockTime = waitLockTime;
    }
}
