package com.jobs.layers.Exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;


public class EmailInUseException extends Exception {
    public HttpStatus status;
    public String message;

    public EmailInUseException(HttpStatus status ,String message){
        super(message);

        this.message = message;
       this.status = status;
    }


}
