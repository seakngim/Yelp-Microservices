package kh.edu.cstad.notification.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private Template template;
    private Channel channel;
    private String recipient;
    private DeliveryStatus status;
    private String content;
}
