package com.jobs.layers.Exceptions;

import org.springframework.http.HttpStatus;

public class UnAuthenticatedAccountAccessException extends Exception{
    public HttpStatus status;
    public String message;

    public UnAuthenticatedAccountAccessException(HttpStatus status, String message){
        super(message);

        this.message = message;
        this.status = status;
    }
}
