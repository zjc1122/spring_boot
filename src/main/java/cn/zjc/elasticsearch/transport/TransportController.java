package cn.zjc.elasticsearch.transport;


import cn.zjc.model.es.Article;
import cn.zjc.model.es.ArticleUser;
import cn.zjc.util.JsonResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by zhangjiacheng on 2017/12/27.
 */

@RestController
@RequestMapping(value = "/transport")
public class TransportController {

    @Autowired
    private TransportClient transportClient;

    @Resource
    private TransportClientRepository client;

    private static final Logger logger = LoggerFactory.getLogger(TransportController.class);

    @RequestMapping("/addes")
    public String AddIndex() throws IOException {
        IndexRequestBuilder prepareIndex = transportClient.prepareIndex("articles", "article");
        /**
         * 使用es的拼接方式
         */
        XContentBuilder xContentBuilder = jsonBuilder().startObject().field("author", "保尔柯察金")
                .field("publishTime", new Date()).field("title", "钢铁是怎么样练成的").endObject();
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

    @RequestMapping("/createIndexAndDocument")
    public JsonResult createIndexAndDocument(String index, String type) throws JsonProcessingException {
        Article article =Article.builder().articleId(11L).author("aaa").content("bbb").title("ccc").date(new Timestamp(System.currentTimeMillis())).userId(2L).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(article);
        String indexAndDocument = client.createIndexAndDocument(index, type, json);
        return JsonResult.success(indexAndDocument,"添加成功");
    }

    @RequestMapping("/createIndexAndMapping")
    public JsonResult createIndexAndMapping(String index, String type){
        Article article =Article.builder().build();
        ArticleUser articleUser = ArticleUser.builder().build();
        Boolean indexAndMapping = client.createIndexAndMapping(index, type, article);
        if(!indexAndMapping){
            return JsonResult.failed("添加失败");
        }
        return JsonResult.success("添加成功");
    }

    @RequestMapping("/createTypeAndMapping")
    public JsonResult createTypeAndMapping(String index, String type){
        client.createIndex(index);
        Article article =Article.builder().build();
        ArticleUser articleUser = ArticleUser.builder().build();
        Boolean typeAndMapping = client.createTypeAndMapping(index, type, article);
        if(!typeAndMapping){
            return JsonResult.failed("添加失败");
        }
        return JsonResult.success("添加成功");
    }

    @RequestMapping("/deleteIndex")
    public JsonResult deleteIndex(String index){
        Boolean aBoolean = client.deleteIndex(index);

        if(!aBoolean){
            return JsonResult.failed("删除失败");
        }
        return JsonResult.success("删除成功");
    }

}
