package com.example.test.exception;

public class IdNotFoundException extends Throwable {
    public IdNotFoundException(String noId) {
        super (noId);
    }
}
