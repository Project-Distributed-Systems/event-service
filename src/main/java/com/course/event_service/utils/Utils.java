package com.course.event_service.utils;

import com.course.event_service.entities.types.EventOutboxPayload;
import com.course.event_service.exceptions.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class Utils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String writeToString(EventOutboxPayload eventOutboxPayload) {
        String payload;
        try {
            payload = objectMapper.writeValueAsString(eventOutboxPayload);
        } catch (JacksonException e) {
            throw new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error serializing to outbox data.");
        }

        return payload;
    }

    private Utils() {}
}
