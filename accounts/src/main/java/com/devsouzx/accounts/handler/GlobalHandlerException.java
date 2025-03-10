package com.devsouzx.accounts.handler;

import com.devsouzx.accounts.dto.handler.CustomErrorResponse;
import com.devsouzx.accounts.handler.exceptions.ApiAuthorizationException;
import com.devsouzx.accounts.handler.exceptions.ErrorCodeException;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ApiAuthorizationException.class})
    public ResponseEntity<Object> handleApiAuthorizationError(Exception e, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, null, headers, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Object> handleGeneralError(Exception e, WebRequest request) {
        CustomErrorResponse error = new CustomErrorResponse();
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ErrorCodeException.class})
    private ResponseEntity<Object> handleCodeError(Exception e, WebRequest request) {
        ErrorCodeException errorCodeException = (ErrorCodeException) e;
        CustomErrorResponse error = new CustomErrorResponse();
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setCode(errorCodeException.getErrorcode().getCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }
}
