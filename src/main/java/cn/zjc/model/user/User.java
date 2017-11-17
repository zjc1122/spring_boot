package cn.zjc.model.user;

import cn.zjc.model.BaseEntity;
import lombok.Data;
import javax.persistence.*;

@Data
@Table(name = "t_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String password;

    private String phone;

}