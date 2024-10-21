package com.jobs.layers.Handlers.VerificationTokenExceptionHandlers;

import com.jobs.layers.Exceptions.TOKEN_EXPIRED;
import com.jobs.layers.Messages.errorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ExpiredVerificationTokenHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TOKEN_EXPIRED.class)
    public ResponseEntity<errorMessage> genarateException(HttpServletRequest req, TOKEN_EXPIRED exception){
        errorMessage message = new errorMessage(exception.status,req.getServletPath() ,exception.message);
        ResponseEntity<errorMessage> entityResponse = new ResponseEntity<>(message,exception.status);
        return entityResponse;
    }
}
