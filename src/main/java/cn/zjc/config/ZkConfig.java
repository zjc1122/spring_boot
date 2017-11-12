package cn.zjc.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangjiacheng on 2017/11/12
 */
@Configuration
public class ZkConfig {
    @Value("${zk.master.host}")
    private String masterHost;
    @Value("${zk.lockPath}")
    private String lockPath;
    @Value("${zk.tryTime}")
    private Integer tryTime;
    @Value("${zk.tryCount}")
    private Integer tryCount;

    CuratorFramework client;
    RetryPolicy retryPolicy;
    InterProcessMutex lock;

    public CuratorFramework getZkClient(){
        retryPolicy = new ExponentialBackoffRetry(tryTime,tryCount);
        client= CuratorFrameworkFactory.newClient(masterHost, retryPolicy);
        return client;
    }
    public InterProcessMutex getZkLock(){
        lock = new InterProcessMutex(client, lockPath);
        return lock;
    }
}