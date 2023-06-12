package com.lyy.intellijoj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException extends RuntimeException{
    private Object data;
    public CustomException() {}

    public CustomException(String message){
        super(message);
    }

    public CustomException(String message,Object data){
        this(message);
        this.data = data;
    }
    public Object getData() {
        return this.data;
    }
}
