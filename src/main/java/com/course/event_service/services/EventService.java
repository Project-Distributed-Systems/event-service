package com.course.event_service.services;

import com.course.event_service.dto.EventDTO;

import java.util.UUID;

public interface EventService {

    UUID createEvent(EventDTO eventDTO);
    void updateEvent(UUID eventId, EventDTO eventDTO);
    void deleteEvent(UUID eventId);
}

