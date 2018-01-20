package cn.roger.distributed.lock.spring;

import cn.roger.distributed.lock.core.interceptor.AbstractLockAspect;
import cn.roger.distributed.lock.core.interceptor.DisLockInterceptor;
import cn.roger.distributed.lock.core.manager.DisLockConfigurater;

public class LockAspect extends AbstractLockAspect {

    private DisLockConfigurater disLockConfigurater;

    /**
     * 初始化LockAspect
     *
     * @author Roger
     * @date 18-1-20
     */
    public void init() {
        DisLockInterceptor disLockInterceptor = new DisLockInterceptor();
        disLockInterceptor.setDisLockConfigurater(disLockConfigurater);
        setDisLockInterceptor(disLockInterceptor);
    }

    public void setDisLockConfigurater(DisLockConfigurater disLockConfigurater) {
        this.disLockConfigurater = disLockConfigurater;
    }
}
