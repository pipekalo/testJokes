package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionCount extends RuntimeException{
    public ExceptionCount() {
        super("За один раз можно получить не более 100 шуток.");
    }
}
