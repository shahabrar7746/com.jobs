package com.jobs.layers.Responses;

public class Response<B> {

    public B data;
    public String message = "SUCCESSFULL";
    public int StatusCode;
    public Response(B data, int StatusCode){
        this.data = data;
        this.StatusCode = StatusCode;
    }
}
