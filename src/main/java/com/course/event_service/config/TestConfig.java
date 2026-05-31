package com.course.event_service.config;

import com.course.event_service.dto.EventDTO;
import com.course.event_service.entities.types.Category;
import com.course.event_service.entities.types.Modality;
import com.course.event_service.services.EventService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    private final EventService eventService;

    public TestConfig(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) {
        EventDTO eventDTO = new EventDTO("Teste", "Teste", null, null, null,
                Category.valueOf(3), Modality.valueOf(1), null, false);
        UUID eventId = eventService.createEvent(eventDTO);
        EventDTO eventDTO2 = new EventDTO("Name", "Modified", null, null, null,
                Category.valueOf(2), Modality.valueOf(2), null, false);
        eventService.updateEvent(eventId, eventDTO2);
        eventService.deleteEvent(eventId);
        eventService.setCancelled(UUID.fromString("24f861b4-fd7b-420a-8855-e44f891b5f89"));
    }
}
