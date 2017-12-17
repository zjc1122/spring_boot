package cn.zjc.esdao.user;

import cn.zjc.model.user.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}