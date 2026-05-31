package com.course.event_service.exceptions;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    private HttpStatus status;

    public ApiRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
