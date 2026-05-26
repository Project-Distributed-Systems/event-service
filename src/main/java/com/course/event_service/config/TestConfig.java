package com.course.event_service.config;

import com.course.event_service.entities.Event;
import com.course.event_service.entities.enums.Category;
import com.course.event_service.entities.enums.Modality;
import com.course.event_service.repositories.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final EventRepository eventRepository;

    public TestConfig (EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Event event = new Event(null, "Teste", "Teste", null, null, null, Category.valueOf(3), Modality.valueOf(1), null);
        eventRepository.save(event);
    }
}
