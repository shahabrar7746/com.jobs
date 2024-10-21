package com.jobs.layers.Handlers.JobsExceptionHandlers;


import com.jobs.layers.Exceptions.NoJobsFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.jobs.layers.Messages.errorMessage;
@ResponseStatus
@ControllerAdvice
public class JobNotFoundExceptionHandler  extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NoJobsFoundException.class)
    public ResponseEntity<errorMessage> genarateException(HttpServletRequest req, NoJobsFoundException exception){
        errorMessage message = new errorMessage(exception.status, req.getServletPath(),exception.message);
        ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
        return entityResponse;
    }
}
