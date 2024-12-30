package com.ufrn.imd.divide.ai.framework.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatusCode;

    public BusinessException(String message, HttpStatus statusCode) {
        super(message);
        this.httpStatusCode = statusCode;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}