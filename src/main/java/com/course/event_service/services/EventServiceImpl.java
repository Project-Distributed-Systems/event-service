package com.course.event_service.services;

import com.course.event_service.dto.EventDTO;
import com.course.event_service.entities.Event;
import com.course.event_service.entities.EventOutbox;
import com.course.event_service.entities.types.EventOutboxPayload;
import com.course.event_service.exceptions.ApiRequestException;
import com.course.event_service.repositories.EventOutboxRepository;
import com.course.event_service.repositories.EventRepository;
import com.course.event_service.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    @Value("${event.outbox.topic}")
    private String outboxTopic;

    private final EventRepository eventRepository;
    private final EventOutboxRepository outboxRepository;

    public EventServiceImpl(EventRepository eventRepository, EventOutboxRepository outboxRepository) {
        this.eventRepository = eventRepository;
        this.outboxRepository = outboxRepository;
    }

    @Override
    public UUID createEvent(EventDTO eventDTO) {
        Event event = Event.create(eventDTO.name(), eventDTO.description(), eventDTO.startDateTime(),
                eventDTO.endDateTime(), eventDTO.venue(), eventDTO.category(), eventDTO.modality(), eventDTO.creatorId()
        );
        UUID eventId = eventRepository.saveAndFlush(event).getId();
        EventOutboxPayload eventOutboxPayload = new EventOutboxPayload(eventId, true);
        String outboxPayload = Utils.writeToString(eventOutboxPayload);
        outboxRepository.save(EventOutbox.publish(outboxTopic, outboxPayload));
        log.info("Event ID: {}, Status: CREATED", eventId);
        return eventId;
    }

    @Override
    public void updateEvent(UUID eventId, EventDTO eventDTO) {
        Event event = eventRepository.findById(eventId).orElseThrow( ()
                -> new ApiRequestException(HttpStatus.NOT_FOUND, "Event does not exist."));

        if (Objects.nonNull(eventDTO.name())) event.setName(eventDTO.name());
        if (Objects.nonNull(eventDTO.description())) event.setDescription(eventDTO.description());
        if (Objects.nonNull(eventDTO.startDateTime())) event.setStartDateTime(eventDTO.startDateTime());
        if (Objects.nonNull(eventDTO.endDateTime())) event.setEndDateTime(eventDTO.endDateTime());
        if (Objects.nonNull(eventDTO.venue())) event.setVenue(eventDTO.venue());
        if (Objects.nonNull(eventDTO.category())) event.setCategory(eventDTO.category());
        if (Objects.nonNull(eventDTO.modality())) event.setModality(eventDTO.modality());

        log.info("Event ID: {}, Updated data.", eventId);
        EventOutboxPayload eventOutboxPayload = new EventOutboxPayload(eventId, true);
        String outboxPayload = Utils.writeToString(eventOutboxPayload);
        outboxRepository.save(EventOutbox.publish(outboxTopic, outboxPayload));
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(UUID eventId) {
        if (!eventRepository.existsById(eventId)) throw new ApiRequestException(HttpStatus.NOT_FOUND,
                "Event does not exist.");
        eventRepository.deleteById(eventId);
        EventOutboxPayload eventOutboxPayload = new EventOutboxPayload(eventId, false);
        String outboxPayload = Utils.writeToString(eventOutboxPayload);
        outboxRepository.save(EventOutbox.publish(outboxTopic, outboxPayload));
        log.info("Event ID: {}, Status: DELETED.", eventId);
    }
}
