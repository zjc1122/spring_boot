package cn.zjc.elasticsearch.transport;


import cn.zjc.model.es.Article;
import cn.zjc.model.es.ArticleUser;
import cn.zjc.util.GsonHolder;
import cn.zjc.util.JsonResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by zhangjiacheng on 2017/12/27.
 */
@Slf4j
@RestController
@RequestMapping(value = "/transport")
public class TransportController {

    @Autowired
    private TransportClient transportClient;

    @Resource
    private TransportClientRepository client;

    private static final Logger logger = LoggerFactory.getLogger(TransportController.class);

    @RequestMapping("/addIndex")
    public String AddIndex() throws IOException {
        IndexRequestBuilder prepareIndex = transportClient.prepareIndex("articles", "article");
        //使用es帮助类的拼接方式
        XContentBuilder xContentBuilder = jsonBuilder().startObject().field("author", "保尔柯察金").field("publishTime", new Date()).field("title", "钢铁是怎么样练成的").endObject();
        //采用json的格式，存储数据
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
        Article article = Article.builder().articleId(11L).author("aaa").content("bbb").title("ccc").date(LocalDateTime.now()).userId(2L).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(article);
        String indexAndDocument = client.createIndexAndDocument(index, type, json);
        return JsonResult.success(indexAndDocument, "添加成功");
    }

    /**
     * 创建索引并添加映射(不包含文档数据)
     *
     * @param index
     * @param type
     * @return
     */
    @RequestMapping("/createIndexAndMapping")
    public JsonResult createIndexAndMapping(String index, String type) {
        Article article = Article.builder().build();
        ArticleUser articleUser = ArticleUser.builder().build();
        Boolean indexAndMapping = client.createIndexAndMapping(index, type, article);
        if (!indexAndMapping) {
            return JsonResult.failed("添加失败");
        }
        return JsonResult.success("添加成功");
    }

    /**
     * 先创建索引index
     * 再创建type和mapping
     * type和mapping存在则进行更新
     * @param index
     * @param type
     * @return
     */
    @RequestMapping("/createTypeAndMapping")
    public JsonResult createTypeAndMapping(String index, String type) {
        client.createIndex(index);
        Article article = Article.builder().build();
        ArticleUser articleUser = ArticleUser.builder().build();
        Boolean typeAndMapping = client.createTypeAndMapping(index, type, article);
        if (!typeAndMapping) {
            return JsonResult.failed("添加失败");
        }
        return JsonResult.success("添加成功");
    }

    /**
     * 删除索引
     * @param index
     * @return
     */
    @RequestMapping("/deleteIndex")
    public JsonResult deleteIndex(String index) {
        Boolean aBoolean = client.deleteIndex(index);

        if (!aBoolean) {
            return JsonResult.failed("删除失败");
        }
        return JsonResult.success("删除成功");
    }

    /**
     * 以json的格式添加文档数据
     * @param index
     * @param type
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/addDocumentforJson")
    public JsonResult addDocumentforJson(String index, String type) {
        Article article = Article.builder().articleId(11L).author("aaa").content("bbb").title("ccc").date(LocalDateTime.now()).userId(2L).build();
        String json = GsonHolder.getDateGson().toJson(article);
        String indexAndDocument = client.createIndexAndDocument(index, type, json);
        return JsonResult.success(indexAndDocument, "添加成功");
    }

    /**
     * 以XContentBuilder的格式添加文档数据
     * @param index
     * @param type
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/addDocumentforBuilder")
    public JsonResult addDocumentforBuilder(String index, String type) {
        Article article = Article.builder().articleId(11L).author("aaa").content("bbb").title("ccc").date(LocalDateTime.now()).userId(2L).build();
        String indexAndDocument = client.createIndexAndDocument(index, type, article);

        return JsonResult.success(indexAndDocument, "添加成功");
    }

    /**
     * 以XContentBuilder的格式更新文档数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/updateDocumentforBuilder")
    public JsonResult updateDocumentforBuilder(String index, String type, String id) {
        Article article = Article.builder().articleId(11L).author("zzz").content("zzz").title("zzz").date(LocalDateTime.now()).userId(2L).build();
        client.updateDocumentforBuilder(index, type, id, article);

        return JsonResult.success("更新成功");
    }

    /**
     * 以Json的格式更新文档数据
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/updateDocumentforJson")
    public JsonResult updateDocumentforJson(String index, String type, String id) {
        Article article = Article.builder().articleId(11L).author("yyy").content("yyy").title("yyy").date(LocalDateTime.now()).userId(2L).build();
        String json = GsonHolder.getDateGson().toJson(article);
        client.updateDocumentforJson(index, type, id, json);

        return JsonResult.success("更新成功");
    }

    /**
     * 根据ID查询一条文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/getDocument")
    public JsonResult getDocument(String index, String type, String id) {
        String json = client.getDocument(index, type, id);
        Article article = JSON.parseObject(json, new TypeReference<Article>() {
        });
        return JsonResult.success(article);
    }

    /**
     * 根据ID删除一条文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/deleteDocument")
    public JsonResult deleteDocument(String index, String type, String id) {
        Integer status = client.deleteDocument(index, type, id);
        if (!status.equals(200)) {
            return JsonResult.success("删除失败", status);
        }
        return JsonResult.success("删除成功", status);
    }

    @RequestMapping("/matchAllQuery")
    public JsonResult matchAllQuery(String index) throws Exception {
        SearchResponse searchResponse = client.matchAllQuery(index);
        ArrayList<Article> list = Lists.newArrayList();
        for (SearchHit searchHit : searchResponse.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
            Article article = GsonHolder.getDateGson().fromJson(sourceAsString, Article.class);
            list.add(article);
        }
        return JsonResult.success(list);
    }

    public static void main(String[] args) {
        String str = "{\"articleId\":\"11\",\"title\":\"ccc\",\"content\":\"bbb\",\"author\":\"aaa\",\"date\":\"2018-03-19T15:05:01.063\",\"userId\":\"2\"}";
        Article article = GsonHolder.getDateGson().fromJson(str, Article.class);

        System.out.println(LocalDateTime.now());

        /*String str = "2018-03-21T18:25:50.032";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime parse = LocalDateTime.parse(str, formatter);*/

        System.out.println();
    }

}
