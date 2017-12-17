package cn.zjc.model.user;

import cn.zjc.model.BaseEntity;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Data
@Table(name = "t_user")
@Document(indexName="projectname",type="t_user",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String password;

    private String phone;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}