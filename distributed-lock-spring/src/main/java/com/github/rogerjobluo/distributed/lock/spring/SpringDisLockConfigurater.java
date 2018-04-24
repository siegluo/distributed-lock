package com.github.rogerjobluo.distributed.lock.spring;

import com.github.rogerjobluo.distributed.lock.core.manager.DisLockConfig;
import com.github.rogerjobluo.distributed.lock.core.manager.DisLockConfigurater;
import com.github.rogerjobluo.distributed.lock.core.manager.DisLockManager;
import com.github.rogerjobluo.distributed.lock.core.repository.DefaultDislockRepository;
import com.github.rogerjobluo.distributed.lock.core.repository.DisLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;

public class SpringDisLockConfigurater implements DisLockConfigurater {

    private DisLockManager disLockManager;

    private DisLockRepository disLockRepository;

    @Autowired(required = false)
    //如果
    private DisLockConfig disLockConfig = DefaultDisLockConfig.INSTANCE;

    private LockRegistry lockRegistry;

    /**
     * 初始化 DisLockConfigurater 将 DisLockManager 与 lockRegistry 等初始化
     *
     * @author Roger
     * @date 18-1-20 上午11:21
     */
    public void init() {
        disLockManager = new DisLockManager();
        disLockRepository = new DefaultDislockRepository();
        disLockRepository.setLockRegistry(lockRegistry);
        disLockManager.setDisLockRepository(disLockRepository);
    }

    @Override
    public DisLockManager getDisLockManager() {
        return disLockManager;
    }

    @Override
    public DisLockConfig getDisLockConfig() {
        return this.disLockConfig;
    }

    public void setLockRegistry(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }
}
