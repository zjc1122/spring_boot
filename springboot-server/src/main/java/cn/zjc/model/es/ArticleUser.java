package cn.zjc.model.es;

import cn.zjc.elasticsearch.ESearchTypeColumn;
import cn.zjc.model.BaseEntity;
import lombok.*;

import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ArticleUser{

    @ESearchTypeColumn(type = "long")
    private Long userId;

    @ESearchTypeColumn
    private String userName;

    @ESearchTypeColumn
    private String password;

    @ESearchTypeColumn(analyze = true)
    private String phone;

}