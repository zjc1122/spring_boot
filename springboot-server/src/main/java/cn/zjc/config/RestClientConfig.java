package cn.zjc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zhangjiacheng
 * @ClassName : RestClientConfig
 * @date : 2018/6/11
 * @Description : RestClient配置类
 */
@Slf4j
@Configuration
public class RestClientConfig implements FactoryBean<RestClient>, InitializingBean, DisposableBean {


    @Value("${httpHost.host}")
    private String host;
    @Value("${httpHost.port}")
    private Integer port;
    @Value("${httpHost.schema}")
    private String schema;
    @Value("${esclient.connectNum}")
    private Integer connectNum;
    @Value("${esclient.connectPerRoute}")
    private Integer connectPerRoute;

    private RestClientBuilder restClientBuilder;
    private RestClient restClient;

    private static final int CONNECT_TIME_OUT = 5000;
    private static final int SOCKET_TIME_OUT = 60000;
    private static final int CONNECTION_REQUEST_TIME_OUT = 5000;

    @Override
    public void destroy(){
        try {
            log.info("Closing elasticSearch client");
            if (restClient != null) {
                restClient.close();
            }
        } catch (final Exception e) {
            log.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public RestClient getObject(){
        return restClient;
    }

    @Override
    public Class<RestClient> getObjectType() {
        return RestClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet(){
        buildClient();
    }

    private void buildClient() {
        restClientBuilder = RestClient
                .builder(httpHost());
        setHttpClientConfigCallback();
        setRequestConfigCallback();
        restClient = restClientBuilder.build();
        log.info("ElasticsearchClient RestClient 初始化成功");
    }

    private HttpHost httpHost() {
        return new HttpHost(host, port, schema);
    }

    /**
     * 异步httpclient的连接数配置
     */
    private void setHttpClientConfigCallback() {
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(connectNum);
            httpClientBuilder.setMaxConnPerRoute(connectPerRoute);
            return httpClientBuilder;
        });
    }

    /**
     * 异步httpclient的连接延时配置
     */
    private void setRequestConfigCallback() {
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIME_OUT);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIME_OUT);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
            return requestConfigBuilder;
        });
    }
}
