package com.example.queue.exceptions;

public class BadTokenException extends RuntimeException{
    public BadTokenException() {
        super("bad token");
    }
}
