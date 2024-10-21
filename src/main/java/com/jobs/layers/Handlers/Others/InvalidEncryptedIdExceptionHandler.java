package com.jobs.layers.Handlers.Others;

import com.jobs.layers.Exceptions.InvalidEncryptedIdException;
import com.jobs.layers.Exceptions.InvalidUserTypeException;
import com.jobs.layers.Messages.errorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ResponseStatus
@ControllerAdvice

public class InvalidEncryptedIdExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidEncryptedIdException.class)
    public ResponseEntity<errorMessage> catchException(HttpServletRequest req, InvalidEncryptedIdException exception){
        errorMessage message = new errorMessage(exception.status,req.getServletPath() ,exception.message);
        ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
        return entityResponse;
    }
}
