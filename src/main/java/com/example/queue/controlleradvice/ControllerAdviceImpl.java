package com.example.queue.controlleradvice;

import com.example.queue.exceptions.BadTokenException;
import com.example.queue.exceptions.BadSecretKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdviceImpl extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadTokenException.class })
    protected ResponseEntity<Object> handleConflict(BadTokenException ex, WebRequest request) {
        log.error(ex.getMessage());
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = { BadSecretKeyException.class })
    protected ResponseEntity<Object> handleConflict(BadSecretKeyException ex, WebRequest request) {
        log.error(ex.getMessage());
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
