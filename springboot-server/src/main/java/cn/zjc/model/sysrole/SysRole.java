package cn.zjc.model.sysrole;

import cn.zjc.model.BaseEntity;
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
public class SysRole extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String name;

    private String desc;

    private Integer enable;

    private Date createdAt;

    private Date updatedAt;

    @Override
    public String getAuthority() {
        return name;
    }
}