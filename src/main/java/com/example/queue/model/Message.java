package com.example.queue.model;

import com.example.queue.model.enums.Status;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Message {
    private String topic;
    private String payload;

    public Queue toQueue(UUID source) {
        Queue queue = new Queue();
        queue.setPayload(payload);
        queue.setSource(source);
        queue.setStatus(Status.WAITING);
        return queue;
    }
}
