package cn.zjc.controller.user;

import cn.zjc.es.test.PostRepository;
import cn.zjc.model.user.User;
import cn.zjc.server.user.UserService;
import cn.zjc.test.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Created by zhangjiacheng on 2017/11/16.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @RequestMapping(value = "/all")
    public Object findAllUser(){
        User user = new User();
        System.out.println("===========");
        log.info("===========");
        return userService.selectPageAll(1,15,user);
    }
    @RequestMapping(value = "/add")
    public String addUser(){
        User user = new User();
        log.info("===========");
        user.setUserId(5);
        user.setPassword("springboot");
        user.setPhone("springboot_111");
        user.setUserName("springboot_222");
        userService.save(user);
        return "success";
    }
    @RequestMapping("/query")
    public Object testSearch(String word, @PageableDefault Pageable pageable) {
//        String queryString = "springboot";//搜索关键字
//        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
//        Iterable<User> searchResult = userSearchRepository.search(builder);
//        Iterator<User> iterator = searchResult.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
        List<Post> articles = elasticsearchTemplate.queryForList(searchQuery, Post.class);
        for (Post article : articles) {
            System.out.println(article.toString());
        }
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }
}
