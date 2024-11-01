package kh.edu.cstad.notification.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "templates")
public class Template {
    @Id
    private String id;
    private String name;
    private String content;
}
