package com.jobs.layers.Handlers.EmailExceptionHandlers;

import com.jobs.layers.Exceptions.EmailNotFoundException;
import com.jobs.layers.Messages.errorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus
@ControllerAdvice
public class EmailNotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<errorMessage> catchException(HttpServletRequest req, EmailNotFoundException exception){

        errorMessage message = new errorMessage(exception.status,req.getServletPath() ,exception.message);
        ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
        return entityResponse;
    }
}
