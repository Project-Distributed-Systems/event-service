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
    public void run(String... args) throws Exception {
        EventDTO eventDTO = new EventDTO("Teste", "Teste", null, null, null,
                Category.valueOf(3), Modality.valueOf(1), null, false);
        eventService.createEvent(eventDTO);
        EventDTO eventDTO2 = new EventDTO("Name", null, null, null, null,
                Category.valueOf(2), Modality.valueOf(2), null, false);
        eventService.updateEvent(UUID.fromString("800c55d3-f80b-443c-bcf6-e22ac42416ee"), eventDTO2);
    }
}
