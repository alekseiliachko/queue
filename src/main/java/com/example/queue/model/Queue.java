package com.example.queue.model;

import com.example.queue.model.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Document
public class Queue {

    @Id
    private UUID id;
    private Status status;
    private String payload;
    private String topic;
    private String destination;
    private Long retriesLeft;
    private Date arrived;
    private Date handled;

    public Message toMessage() {
        Message message = new Message();
        message.setTopic(topic);
        message.setPayload(payload);
        return message;
    }
}
