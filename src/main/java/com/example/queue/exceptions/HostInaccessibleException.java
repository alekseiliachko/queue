package com.example.queue.exceptions;

public class HostInaccessibleException extends RuntimeException{
    public HostInaccessibleException(String host) {
        super("host inaccessible: " + host);
    }
}
