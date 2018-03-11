package cn.zjc.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhangjiacheng on 2018/03/11.
 */
public class TransportClientRepository {

    @Autowired
    private TransportClient transportClient;

    private static final Logger logger = LoggerFactory.getLogger(TransportClientRepository.class);


}
