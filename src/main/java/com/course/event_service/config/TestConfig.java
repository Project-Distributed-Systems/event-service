package com.course.event_service.config;

import com.course.event_service.entities.Event;
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
        Event event = new Event(null, "Teste", "Teste");
        eventRepository.save(event);
    }
}
