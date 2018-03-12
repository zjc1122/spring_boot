package cn.zjc.elasticsearch.test;


import cn.zjc.elasticsearch.TransportClientRepository;
import cn.zjc.model.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by zhangjiacheng on 2017/12/27.
 */

@RestController
@RequestMapping(value = "/post")
public class PostController {

//    @Autowired
//    private PostRepository postRepository;
    @Autowired
    private TransportClient transportClient;

    @Resource
    TransportClientRepository client;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @RequestMapping("/addes")
    public String AddIndex() throws IOException {
        IndexRequestBuilder prepareIndex = transportClient.prepareIndex("articles", "article");
        /**
         * 使用es的拼接方式
         */
        XContentBuilder xContentBuilder = jsonBuilder().startObject().field("author", "保尔柯察金")
                .field("publishTime", new Date()).field("title", "钢铁是怎么样练成的").endObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(Article.class);
        /**
         * 采用json的形式
         */
        prepareIndex.setSource(xContentBuilder);
        IndexResponse indexResponse = prepareIndex.get();
        // Index name 数据库名称

        String _index = indexResponse.getIndex();
        logger.info("数据库名称:" + _index);
        // Type name 表的名称

        String _type = indexResponse.getType();
        logger.info("表的名称" + _type);
        // Document ID (generated or not)

        String _id = indexResponse.getId();
        logger.info("唯一ID" + _id);
        // Version 版本,如果是第一次创建则为1

        long _version = indexResponse.getVersion();
        logger.info("版本" + _version);
        // status has stored current instance statement.

        RestStatus status = indexResponse.status();
        logger.info("状态 :" + status);
        logger.info("创建成功!!!");
        return "成功";
    }

    @RequestMapping("/getIndex")
    public String getIndex() throws IOException {
        // 查询所有

        // GetResponse getResponse = transportClient.prepareGet().get();

        // String string = getResponse.getSourceAsString();

        // 在知道id的情况下使用

        String id = "AWIVi8M4QaDxaSKrjebA";
        GetResponse getResponse = transportClient.prepareGet("articles", "article", id).get();
        logger.info(getResponse.getSourceAsString());
        return getResponse.getSourceAsString();
    }
    @RequestMapping("/adds")
    public String adds(){
        Article article =Article.builder().author("aaa").content("bbb").title("ccc").date("2018-03-24").build();

        String s = client.saveDoc("index", "type", "444", article);
        return s;
    }
    @RequestMapping("/CreateIndexAndMapping")
    public void ss() throws IOException {
        CreateIndexRequestBuilder cib=transportClient.admin().indices().prepareCreate("zjc");
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties") //设置之定义字段
                .startObject("author")
                .field("type","string") //设置数据类型
                .endObject()
                .startObject("title")
                .field("type","string")
                .endObject()
                .startObject("content")
                .field("type","string")
                .endObject()
                .startObject("price")
                .field("type","string")
                .endObject()
                .startObject("view")
                .field("type","string")
                .endObject()
                .startObject("tag")
                .field("type","string")
                .endObject()
                .startObject("date")
                .field("type","date")  //设置Date类型
                .field("format","yyyy-MM-dd HH:mm:ss") //设置Date的格式
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping("zjc-1", mapping);

        CreateIndexResponse res=cib.execute().actionGet();

        System.out.println("----------添加映射成功----------");
    }
}
