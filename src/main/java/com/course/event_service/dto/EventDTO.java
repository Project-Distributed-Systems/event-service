package com.course.event_service.dto;

import com.course.event_service.entities.types.Category;
import com.course.event_service.entities.types.Modality;

import java.time.LocalDateTime;

public record EventDTO(
        String name,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String venue,
        Category category,
        Modality modality,
        String creatorId,
        boolean cancelled
) {
}
