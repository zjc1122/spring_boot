package cn.zjc.model;

import cn.zjc.elasticsearch.ESearchTypeColumn;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@Builder
@ToString
public class Article {

    @Id
    @ESearchTypeColumn
    private String id;

    @ESearchTypeColumn
    private String title;

    private String content;

    private String author;

    private String date;

}
