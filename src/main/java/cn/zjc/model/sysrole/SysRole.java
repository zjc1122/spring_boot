package cn.zjc.model.sysrole;

import cn.zjc.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private Byte enable;

    private Date createdAt;

    private Date updatedAt;

}