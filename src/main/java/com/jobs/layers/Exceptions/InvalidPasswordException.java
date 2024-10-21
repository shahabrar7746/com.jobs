package com.jobs.layers.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter


public class InvalidPasswordException extends Exception{
    public HttpStatus status;
    public String message;

    public InvalidPasswordException(HttpStatus status, String message){
        super(message)
        ;


        this.message = message;
        this.status = status;
    }
}
