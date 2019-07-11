package cn.roger.distributed.lock.redis.starter;

import org.redisson.config.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author jie.luo
 */
@ConfigurationProperties(prefix = "distributed.lock.redis")
public class RedisLockProperties {
    private String type = "single";
    @NestedConfigurationProperty
    private SingleServerConfig singleServerConfig;
    @NestedConfigurationProperty
    private ClusterServersConfig clusterServersConfig;
    @NestedConfigurationProperty
    private ReplicatedServersConfig replicatedServersConfig;
    @NestedConfigurationProperty
    private MasterSlaveServersConfig masterSlaveServersConfig;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public ReplicatedServersConfig getReplicatedServersConfig() {
        return replicatedServersConfig;
    }

    public void setReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) {
        this.replicatedServersConfig = replicatedServersConfig;
    }

    public MasterSlaveServersConfig getMasterSlaveServersConfig() {
        return masterSlaveServersConfig;
    }

    public void setMasterSlaveServersConfig(MasterSlaveServersConfig masterSlaveServersConfig) {
        this.masterSlaveServersConfig = masterSlaveServersConfig;
    }
}

