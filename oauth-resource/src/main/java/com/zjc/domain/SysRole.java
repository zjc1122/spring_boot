package com.zjc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRole implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String desc;

    private Integer enable;

    private Date createdAt;

    private Date updatedAt;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}