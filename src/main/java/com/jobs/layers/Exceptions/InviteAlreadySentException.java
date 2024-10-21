package com.jobs.layers.Exceptions;

import org.springframework.http.HttpStatus;

public class InviteAlreadySentException extends Exception{
    public HttpStatus status;
    public String message;

    public InviteAlreadySentException(HttpStatus status, String message){
        super(message);

        this.message = message;
        this.status = status;
    }
}
