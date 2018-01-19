package cn.roger.distributed.lock.spring;

import cn.roger.distributed.lock.core.manager.DisLockConfig;
import cn.roger.distributed.lock.core.manager.DisLockConfigurater;
import cn.roger.distributed.lock.core.manager.DisLockManager;
import cn.roger.distributed.lock.core.repository.DefaultDislockRepository;
import cn.roger.distributed.lock.core.repository.DisLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;

public class SppringDisLockConfigurater implements DisLockConfigurater {

    private DisLockManager disLockManager;

    private DisLockRepository disLockRepository;

    @Autowired(required = false)
    private DisLockConfig disLockConfig = DefaultDisLockConfig.INSTANCE;

    @Autowired
    private LockRegistry lockRegistry;

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
}
