package com.example.queue.model;

import com.example.queue.model.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document

public class Queue {

    @Id
    private UUID id;
    private String source;
    private String dest;
    private Status status;
    private String payload;
}
