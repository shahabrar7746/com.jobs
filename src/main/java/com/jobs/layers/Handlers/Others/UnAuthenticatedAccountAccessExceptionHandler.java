package com.jobs.layers.Handlers.Others;

import com.jobs.layers.Exceptions.UnAuthenticatedAccountAccessException;
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
public class UnAuthenticatedAccountAccessExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UnAuthenticatedAccountAccessException.class)
public ResponseEntity<errorMessage> catchException(HttpServletRequest req, UnAuthenticatedAccountAccessException exception){
    errorMessage message = new errorMessage(exception.status,req.getServletPath() ,exception.message);
    ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
    return entityResponse;
}
}
