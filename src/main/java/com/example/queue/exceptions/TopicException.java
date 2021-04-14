package com.example.queue.exceptions;

public class TopicException extends RuntimeException{
    public TopicException(String topic) {
        super("topic error: " + topic);
    }
}
