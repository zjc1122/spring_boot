package cn.zjc.model.es;

import cn.zjc.elasticsearch.ESearchTypeColumn;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class Article {

    @ESearchTypeColumn(type = "long")
    private Long articleId;

    @ESearchTypeColumn(analyze = true)
    private String title;

    @ESearchTypeColumn(analyze = true)
    private String content;

    @ESearchTypeColumn
    private String author;

    @ESearchTypeColumn(type = "date")
    private LocalDateTime date;

    @ESearchTypeColumn(type = "long")
    private Long userId;

}
