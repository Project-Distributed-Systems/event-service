package com.course.event_service.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_outbox")
public class EventOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false, length = 4096)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public EventOutbox() {}

    public EventOutbox(String topic, String payload, LocalDateTime timestamp) {
        this.topic = topic;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public static EventOutbox publish(String topic, String payload) {
        return new EventOutbox(topic, payload, LocalDateTime.now());
    }
}
