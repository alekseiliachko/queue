package com.example.queue.exceptions;

public class NonSubbedTopicException extends RuntimeException{
    public NonSubbedTopicException(String topic) {
        super("none is subbed to topic: " + topic);
    }
}
