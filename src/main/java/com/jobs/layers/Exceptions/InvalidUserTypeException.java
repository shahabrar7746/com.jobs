package com.jobs.layers.Exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserTypeException extends Exception{

    public HttpStatus status;
    public String message;

    public InvalidUserTypeException(HttpStatus status, String message){
        super(message);

        this.message = message;
        this.status = status;
    }
}
