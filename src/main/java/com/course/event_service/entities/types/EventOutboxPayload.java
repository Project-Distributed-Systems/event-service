package com.course.event_service.entities.types;

import java.util.UUID;

public record EventOutboxPayload(
        UUID eventId,
        Boolean isActive
) {
}
