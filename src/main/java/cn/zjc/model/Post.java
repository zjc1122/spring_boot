package cn.zjc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Post {
    @Id
    private String id;

    private String title;

    private String content;

    private int userId;

    private int weight;

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", weight=" + weight +
                '}';
    }
}
