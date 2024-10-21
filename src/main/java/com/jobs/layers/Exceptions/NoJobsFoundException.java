package com.jobs.layers.Exceptions;


import org.springframework.http.HttpStatus;

public class NoJobsFoundException extends Exception{

    public HttpStatus status;
        public String message;
    public NoJobsFoundException(String message, HttpStatus status){

        super(message);
    this.message = message;
        this.status = status;
    }
    public NoJobsFoundException(){
        super();
    }
}
