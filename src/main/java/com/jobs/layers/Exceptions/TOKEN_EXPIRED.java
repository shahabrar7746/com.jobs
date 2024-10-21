package com.jobs.layers.Exceptions;

import org.springframework.http.HttpStatus;

public class TOKEN_EXPIRED extends Exception{


    public HttpStatus status;
    public String message;

    public TOKEN_EXPIRED(HttpStatus status,String message){
        super(message);

        this.message = message;
        this.status = status;
    }
}
