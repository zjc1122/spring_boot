package cn.zjc.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangjiacheng on 2018/2/20.
 */
@Configuration
public class ElasticsearchRestClient implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchRestClient.class);

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

    private  RestHighLevelClient restHighLevelClient;
    private  RestClientBuilder builder;
    private  RestClient restClient;
    private  HttpHost httpHost=null;

    private  final int CONNECT_TIME_OUT = 1000;
    private  final int SOCKET_TIME_OUT = 30000;
    private  final int CONNECTION_REQUEST_TIME_OUT = 500;

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (restClient != null) {
                restClient.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    private void buildClient()  {
        if(httpHost==null){
            builder = RestClient.builder(httpHost());
        }
        setConnectTimeOutConfig();
        setMutiConnectConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(restClient);
        logger.info("ElasticsearchClient RestHighLevelClient 连接成功");
    }

    private HttpHost httpHost(){
        return new HttpHost(host,port,schema);
    }

    /**
     *  异步httpclient的连接数配置
     */
    private void setMutiConnectConfig(){
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(connectNum);
            httpClientBuilder.setMaxConnPerRoute(connectPerRoute);
            return httpClientBuilder;
        });
    }
    /**
     * 异步httpclient的连接延时配置
     */
    private void setConnectTimeOutConfig(){
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIME_OUT);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIME_OUT);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT);
            return requestConfigBuilder;
        });
    }
}
