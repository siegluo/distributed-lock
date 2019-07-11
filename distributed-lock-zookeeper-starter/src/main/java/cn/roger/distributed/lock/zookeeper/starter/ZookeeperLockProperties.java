package cn.roger.distributed.lock.zookeeper.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "distributed.lock.zk")
public class ZookeeperLockProperties {
    @NestedConfigurationProperty
    private ZookeeperClientConfig zookeeperClientConfig;

    static class ZookeeperClientConfig {
        private String urls;
        private int sessionTimeout;

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }

        public int getSessionTimeout() {
            return sessionTimeout;
        }

        public void setSessionTimeout(int sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }
    }

    public ZookeeperClientConfig getZookeeperClientConfig() {
        return zookeeperClientConfig;
    }

    public void setZookeeperClientConfig(ZookeeperClientConfig zookeeperClientConfig) {
        this.zookeeperClientConfig = zookeeperClientConfig;
    }
}

