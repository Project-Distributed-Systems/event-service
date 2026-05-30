package com.course.event_service.dto;

import com.course.event_service.entities.types.Category;
import com.course.event_service.entities.types.Modality;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDTO(
        String name,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String venue,
        Category category,
        Modality modality,
        UUID creatorId,
        boolean cancelled
) {
}
