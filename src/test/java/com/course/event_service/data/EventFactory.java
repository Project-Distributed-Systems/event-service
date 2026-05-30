package com.course.event_service.data;

import com.course.event_service.dto.EventDTO;
import com.course.event_service.entities.Event;
import com.course.event_service.entities.types.Category;
import com.course.event_service.entities.types.Modality;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventFactory {

    private final static String NAME = "The Beatles";
    private final static String DESCRIPTION = "The Beatles Concert";
    private final static LocalDateTime START_DATE_TIME = LocalDateTime.parse("2026-05-29T20:00:00");
    private final static LocalDateTime END_DATE_TIME = LocalDateTime.parse("2026-05-29T23:00:00");
    private final static String VENUE = "São Paulo, SP";
    private final static Category CATEGORY = Category.CONCERT;
    private final static Modality MODALITY = Modality.INPERSON;
    private final static UUID CREATOR_ID = UUID.randomUUID();

    public static Event eventInit() {
        return Event.create(NAME, DESCRIPTION, START_DATE_TIME, END_DATE_TIME, VENUE, CATEGORY, MODALITY, CREATOR_ID);
    }

    public static EventDTO eventDTOInit() {
        return new EventDTO(NAME, DESCRIPTION, START_DATE_TIME, END_DATE_TIME, VENUE, CATEGORY, MODALITY, CREATOR_ID,
                Boolean.FALSE);
    }

    public static EventDTO eventDTOUpdate(String venue, String description) {
        return new EventDTO(NAME, description, START_DATE_TIME, END_DATE_TIME, venue, CATEGORY, MODALITY, CREATOR_ID,
                Boolean.FALSE);
    }
}
