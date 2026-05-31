package com.course.event_service.service;

import com.course.event_service.data.EventFactory;
import com.course.event_service.dto.EventDTO;
import com.course.event_service.entities.Event;
import com.course.event_service.entities.EventOutbox;
import com.course.event_service.repositories.EventOutboxRepository;
import com.course.event_service.repositories.EventRepository;
import com.course.event_service.services.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock private EventRepository eventRepository;
    @Mock private EventOutboxRepository outboxRepository;
    @InjectMocks
    private EventServiceImpl eventService;

    @Captor private ArgumentCaptor<Event> eventCaptor;

    Event event;

    @BeforeEach
    void setUp() {
        eventService = new EventServiceImpl(eventRepository, outboxRepository);

        event = EventFactory.eventInit();
    }

    @Test
    void createEvent() {
        EventDTO eventDTO = EventFactory.eventDTOInit();

        when(eventRepository.saveAndFlush(eventCaptor.capture())).thenReturn(event);
        eventService.createEvent(eventDTO);

        assertThat(eventCaptor.getValue()).usingRecursiveComparison()
                .ignoringFields("id", "createdAt").isEqualTo(event);
        verify(eventRepository).saveAndFlush(eventCaptor.capture());
        verify(outboxRepository, times(1)).save(any());
    }

    @Test
    void updateEvent() {
        EventDTO eventUpdate = EventFactory.eventDTOUpdate("New Venue", "New Description");

        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        eventService.updateEvent(event.getId(), eventUpdate);

        verify(eventRepository).save(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getVenue()).isEqualTo("New Venue");
        assertThat(eventCaptor.getValue().getDescription()).isEqualTo("New Description");
    }

    @Test
    void deleteEvent() {
        when(eventRepository.existsById(any())).thenReturn(true);
        eventService.deleteEvent(event.getId());

        verify(eventRepository, times(1)).deleteById(any());
        verify(outboxRepository, times(1)).save(any(EventOutbox.class));
    }

    @Test
    void setEventCancelled() {
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));
        eventService.setCancelled(event.getId());

        verify(eventRepository, times(1)).save(eventCaptor.capture());
        assertThat(eventCaptor.getValue().isCanceled()).isEqualTo(true);
    }
}
