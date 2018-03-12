package cn.zjc.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangjiacheng on 2018/03/11.
 */
@Service
public class TransportClientRepository {

    @Autowired
    private TransportClient transportClient;

    private static final Logger logger = LoggerFactory.getLogger(TransportClientRepository.class);

    /**
     * 创建索引
     * @param index 索引名称
     * @param type 索引类型
     * @param id 索引id
     * @param obj
     * @return
     */
    public String saveDoc(String index, String type, String id, Object obj){
        IndexResponse response = transportClient.prepareIndex(index, type,id).setSource(getXContentBuilderKeyValue(obj)).get();
        return response.getId();
    }

    public  XContentBuilder getXContentBuilderKeyValue(Object o) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            List<Field> fieldList = new ArrayList<Field>();
            Class tempClass = o.getClass();
            while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                tempClass = tempClass.getSuperclass();// 得到父类,然后赋给自己
            }
            for (Field field : fieldList) {
                if(field.isAnnotationPresent(ESearchTypeColumn.class)) {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), o.getClass());
                    Object value =descriptor.getReadMethod().invoke(o);
                    if (value != null) {
                        builder.field(field.getName(),value.toString());
                    }
                }
            }
            builder.endObject();
            logger.info(builder.string());
            return builder;
        } catch (Exception e) {
            logger.error("获取object key-value失败，{}", e.getMessage());
        }
        return null;
    }
}
