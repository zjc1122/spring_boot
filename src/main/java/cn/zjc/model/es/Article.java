package cn.zjc.model.es;

import cn.zjc.elasticsearch.ESearchTypeColumn;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
