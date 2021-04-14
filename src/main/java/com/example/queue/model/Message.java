package com.example.queue.model;

import com.example.queue.config.PathConfig;
import com.example.queue.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class Message {
    @JsonProperty(required = true)
    String payload;

    @JsonProperty(required = true)
    String topic;

    public Queue toQueue(String destination) {
        Queue queue = new Queue();
        queue.setPayload(payload);
        queue.setDestination(destination);
        queue.setTopic(topic);
        queue.setRetriesLeft(PathConfig.MAX_RETRY);
        queue.setStatus(Status.WAITING);
        queue.setArrived(new Date());
        return queue;
    }
}
