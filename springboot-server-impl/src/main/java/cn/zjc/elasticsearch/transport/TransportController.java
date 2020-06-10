package cn.zjc.elasticsearch.transport;


import cn.zjc.model.es.Article;
import cn.zjc.model.es.ArticleUser;
import cn.zjc.util.GsonUtil;
import cn.zjc.util.JsonResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by zhangjiacheng on 2017/12/27.
 */
@Slf4j
@RestController
@RequestMapping(value = "/transport")
public class TransportController {

    @Resource
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
     * 创建索引
     */
    @RequestMapping("/createIndex")
    public JsonResult createIndex(String index) {
        Boolean index1 = client.createIndex(index);
        if (!index1) {
            return JsonResult.failed("添加失败");
        }
        return JsonResult.failed("添加成功");
    }

    /**
     * 创建type和mapping
     * type和mapping存在则进行更新
     *
     * @param index
     * @param type
     * @return
     */
    @RequestMapping("/createTypeAndMapping")
    public JsonResult createTypeAndMapping(String index, String type) {
        Article article = Article.builder().build();
        ArticleUser articleUser = ArticleUser.builder().build();
        Boolean typeAndMapping = client.createTypeAndMapping(index, type, articleUser);
        if (!typeAndMapping) {
            return JsonResult.failed("添加失败");
        }
        return JsonResult.success("添加成功");
    }

    /**
     * 删除索引
     *
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
     *
     * @param index
     * @param type
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/addDocumentforJson")
    public JsonResult addDocumentforJson(String index, String type) {
        Article article = Article.builder().articleId(11L).author("zzz").content("jjj").title("ccc").date(LocalDateTime.now()).userId(333L).build();
        String json = GsonUtil.getLocalDateGson().toJson(article);
        String indexAndDocument = client.createIndexAndDocument(index, type, json);
        return JsonResult.success(indexAndDocument, "添加成功");
    }

    /**
     * 以XContentBuilder的格式添加文档数据
     *
     * @param index
     * @param type
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/addDocumentforBuilder")
    public JsonResult addDocumentforBuilder(String index, String type) {
        Article article = Article.builder().articleId(11L).author("zzz").content("jjj").title("ccc").date(LocalDateTime.now()).userId(333L).build();
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
        Article article = Article.builder().articleId(11L).author("zzz").content("zzz").title("zzz").date(LocalDateTime.now()).userId(1L).build();
        client.updateDocumentforBuilder(index, type, id, article);

        return JsonResult.success("更新成功");
    }

    /**
     * 以Json的格式更新文档数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/updateDocumentforJson")
    public JsonResult updateDocumentforJson(String index, String type, String id) {
        Article article = Article.builder().articleId(11L).author("yyy").content("yyy").title("yyy").date(LocalDateTime.now()).userId(2L).build();
        String json = GsonUtil.getLocalDateGson().toJson(article);
        client.updateDocumentforJson(index, type, id, json);

        return JsonResult.success("更新成功");
    }

    /**
     * 根据ID查询一条文档
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/getDocumentById")
    public JsonResult getDocumentById(String index, String type, String id) {
        String json = client.getDocumentById(index, type, id);
        Article article = JSON.parseObject(json, new TypeReference<Article>() {
        });
        return JsonResult.success(article);
    }

    /**
     * 根据ID删除一条文档
     *
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
    public JsonResult matchAllQuery(String index, String type) throws Exception {
        SearchResponse searchResponse = client.matchAllQuery(index, type);
        ArrayList<Article> list = Lists.newArrayList();
        for (SearchHit searchHit : searchResponse.getHits()) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
            Article article = GsonUtil.getLocalDateGson().fromJson(sourceAsString, Article.class);
            list.add(article);
        }
        return JsonResult.success(list);
    }

    @RequestMapping("/queryByFilter")
    public JsonResult queryByFilter(String index, String type, String field, String value) {
        List<String> queryList = client.queryByFilter(index, type, field, value);
        ArrayList<Article> articles = Lists.newArrayList();
        for (String json : queryList) {
            Article article = GsonUtil.getDateGson().fromJson(json, Article.class);
            articles.add(article);
        }
        return JsonResult.success(articles);
    }

    @RequestMapping("/deleteByQuery")
    public JsonResult deleteByQuery(String index, String field, String value) {
        Long count = client.deleteByQuery(index, field, value);
        if (count == 0) {
            return JsonResult.success("没有符合条件的数据！");
        }
        return JsonResult.success("删除" + count + "条数据！");
    }

    @RequestMapping("/min")
    public JsonResult min(String index, String field) {
        Double min = client.min(index, field);
        return JsonResult.success(min);
    }

}
