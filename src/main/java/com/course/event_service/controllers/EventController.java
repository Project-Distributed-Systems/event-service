package com.course.event_service.controllers;

import com.course.event_service.dto.EventDTO;
import com.course.event_service.dto.ResponseObject;
import com.course.event_service.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createEvent(@RequestBody EventDTO eventDTO) {
        String message = "Event successfully created";
        UUID eventId = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(ResponseObject.of(message, HttpStatus.CREATED, eventId), HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ResponseObject> updateEvent(@RequestBody EventDTO eventDTO, @PathVariable UUID id) {
        String message = "Event successfully updated";
        eventService.updateEvent(id, eventDTO);
        return new ResponseEntity<>(ResponseObject.of(message, HttpStatus.OK, null), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ResponseObject> deleteEvent(@PathVariable UUID id) {
        String message = "Event successfully deleted";
        eventService.deleteEvent(id);
        return new ResponseEntity<>(ResponseObject.of(message, HttpStatus.OK, null), HttpStatus.OK);
    }

    @PatchMapping(path = "{id}/cancel")
    public ResponseEntity<ResponseObject> cancelEvent(@PathVariable UUID id) {
        String message = "Event successfully canceled";
        eventService.setCancelled(id);
        return new ResponseEntity<>(ResponseObject.of(message, HttpStatus.OK, null), HttpStatus.OK);
    }
}
