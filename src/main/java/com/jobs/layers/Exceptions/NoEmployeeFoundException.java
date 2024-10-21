package com.jobs.layers.Exceptions;

import org.springframework.http.HttpStatus;

public class NoEmployeeFoundException extends Exception{


    public String message;
    public HttpStatus status;
    public NoEmployeeFoundException(HttpStatus status, String message){
        super(message);
        this.message = message;
        this.status = status;
    }
}
