package com.example.queue.exceptions;

public class BadSecretKeyException extends RuntimeException {
    public BadSecretKeyException() {
        super("bad secret key");
    }
}
