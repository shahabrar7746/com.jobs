package com.jobs.layers.Exceptions;

import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends Exception{

    public String message;
    public HttpStatus status;

    public EmailNotFoundException(HttpStatus status, String message){
        super(message);

        this.message = message;
        this.status = status;
    }
}
