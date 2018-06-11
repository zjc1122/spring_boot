package cn.zjc.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : ZkClient
 * @author : zhangjiacheng
 * @date : 2018/6/11
 * @Description : zk配置类
 */
@Configuration
public class ZkClient {
    @Value("${zk.host}")
    private String masterHost;
    @Value("${zk.lockPath}")
    private String lockPath;
    @Value("${zk.tryTime}")
    private Integer tryTime;
    @Value("${zk.tryCount}")
    private Integer tryCount;
    @Value("${zk.sessionOutTime}")
    private Integer sessionOutTime;

    public CuratorFramework getZkClient(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(tryTime,tryCount);
        CuratorFramework client= CuratorFrameworkFactory.builder()
                .connectString(masterHost)
                .sessionTimeoutMs(sessionOutTime)
                .retryPolicy(retryPolicy)
                .build();
        return client;
    }
    public InterProcessMutex getZkLock(CuratorFramework client){
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        return lock;
    }
}
