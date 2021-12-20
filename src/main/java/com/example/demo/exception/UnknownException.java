package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class UnknownException extends RuntimeException {
    public UnknownException(String message) {
        super(message);
    }
}
