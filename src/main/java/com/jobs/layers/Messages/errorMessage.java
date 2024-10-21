package com.jobs.layers.Messages;


import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class errorMessage {

    public HttpStatus status;
    public String message;
    public int code;
public String path;
public errorMessage(HttpStatus status,String path,String message) {
this.status = status;
this.path = path;
this.message = message;
    code = status.value();

}}
