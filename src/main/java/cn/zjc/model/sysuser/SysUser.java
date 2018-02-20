package cn.zjc.model.sysuser;

import cn.zjc.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "sys_user")
public class SysUser extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String loginName;

    private String username;

    private String password;

    private String salt;

    private Integer enable = NumberUtils.INTEGER_ONE;

    private Date createdAt;

    private Date updatedAt;

    private Collection<? extends GrantedAuthority> authorities;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    public boolean isEnabled = true;

}