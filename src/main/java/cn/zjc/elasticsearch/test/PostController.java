package cn.zjc.elasticsearch.test;

//import cn.zjc.elasticsearch.service.PostRepository;
import cn.zjc.model.Post;
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
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

//    @RequestMapping("/query")
//    public Object testSearch(String word, @PageableDefault Pageable pageable) {
////        String queryString = "11";//搜索关键字
////        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
////        Iterable<Post> searchResult = postRepository.search(builder);
////        Iterator<Post> iterator = searchResult.iterator();
////        while (iterator.hasNext()) {
////            System.out.println(iterator.next());
////        }
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
//        List<Post> articles = elasticsearchTemplate.queryForList(searchQuery, Post.class);
//        for (Post article : articles) {
//            System.out.println(article.toString());
//        }
//        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
//    }
//    @RequestMapping("/addes")
//    public void testSaveArticleIndex() {
//        Post post = new Post();
//        post.setId("springboot integreate elasticsearch");
//        post.setUserId(1);
//        post.setWeight(1);
//        post.setContent("zjc elasticsearch based on lucene,"
//                + "spring-data-elastichsearch based on elaticsearch"
//                + ",this tutorial tell you how to integrete springboot with spring-data-elasticsearch");
//        post.setWeight(11);
//        postRepository.save(post);
//    }
    @RequestMapping("/addes")
    public String AddIndex() throws IOException {
        IndexRequestBuilder prepareIndex = transportClient.prepareIndex("articles", "article");
        /**
         * 使用es的拼接方式
         */
        XContentBuilder xContentBuilder = jsonBuilder().startObject().field("author", "保尔柯察金")
                .field("publishTime", new Date()).field("title", "钢铁是怎么样练成的").endObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(Post.class);
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

        String id = "AWCcZv3P9bG5N_17j_VU";
        GetResponse getResponse = transportClient.prepareGet("articles", "article", id).get();
        logger.info(getResponse.getSourceAsString());
        return getResponse.getSourceAsString();
    }
}
