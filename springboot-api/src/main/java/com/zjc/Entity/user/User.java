package com.zjc.Entity.user;

import com.zjc.Entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "t_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String password;

    private String phone;

}