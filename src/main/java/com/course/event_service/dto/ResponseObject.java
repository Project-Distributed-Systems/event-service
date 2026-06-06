package com.course.event_service.dto;

import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ResponseObject {

    private String message;
    private HttpStatus status;
    private Object data;
    private ZonedDateTime timestamp;

    public ResponseObject() {}

    public ResponseObject(String message, HttpStatus status, Object data, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static ResponseObject of(String message, HttpStatus status, Object data) {
        return new ResponseObject(message, status, data, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
    }

    public String getMessage() {
        return message;
    }
}
