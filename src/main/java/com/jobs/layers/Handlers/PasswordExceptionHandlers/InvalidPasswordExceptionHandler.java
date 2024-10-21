package com.jobs.layers.Handlers.PasswordExceptionHandlers;

import com.jobs.layers.Exceptions.InvalidPasswordException;
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
public class InvalidPasswordExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<errorMessage> catchException(HttpServletRequest req, InvalidPasswordException exception){
        errorMessage message = new errorMessage(exception.status, req.getServletPath(),exception.message);
        ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
        return entityResponse;
    }
}
