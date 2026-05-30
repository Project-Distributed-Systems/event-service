package com.course.event_service.utils;

import com.course.event_service.entities.types.EventOutboxPayload;
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
            throw new RuntimeException(e);
        }

        return payload;
    }

    private Utils() {}
}
