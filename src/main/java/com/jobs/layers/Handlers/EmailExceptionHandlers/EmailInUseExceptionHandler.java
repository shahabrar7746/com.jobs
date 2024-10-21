package com.jobs.layers.Handlers.EmailExceptionHandlers;

import com.jobs.layers.Exceptions.EmailInUseException;
import com.jobs.layers.Messages.errorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus
@ControllerAdvice
public class EmailInUseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<errorMessage> catchException(HttpServletRequest req, EmailInUseException exception){
        errorMessage message = new errorMessage(exception.status, req.getServletPath(),exception.message);
        ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
        return entityResponse;
    }
}
