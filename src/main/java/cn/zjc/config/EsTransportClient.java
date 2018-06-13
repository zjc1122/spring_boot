package cn.zjc.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : zhangjiacheng
 * @ClassName : EsTransportClient
 * @date : 2018/6/11
 * @Description : ES配置类
 */
@Configuration
public class EsTransportClient implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(EsTransportClient.class);

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;
    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    private TransportClient transportClient;

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing TransportClient client");
            if (transportClient != null) {
                transportClient.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing TransportClient client: ", e);
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return transportClient;
    }

    @Override
    public Class<TransportClient> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    private void buildClient() {
        try {
            PreBuiltTransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings());
            if (!"".equals(clusterNodes)) {
                for (String nodes : clusterNodes.split(",")) {
                    String InetSocket[] = nodes.split(":");
                    String Address = InetSocket[0];
                    Integer port = Integer.valueOf(InetSocket[1]);
                    preBuiltTransportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(Address), port));
                }
                transportClient = preBuiltTransportClient;
                logger.info("Elasticsearch TransportClient 连接成功");
            }
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 初始化默认的client
     */
    private Settings settings() {
        Settings settings = Settings.builder()
                //设置ES实例的名称
                .put("cluster.name", clusterName)
                //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .put("client.transport.sniff", true)
                .build();
        transportClient = new PreBuiltTransportClient(settings);
        return settings;
    }
}
