package com.zjc.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "t_user")
@Entity
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private long userId;

    private String userName;

    private String password;

    private String phone;

}