package com.course.event_service.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_outbox")
public class EventOutBox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public EventOutBox() {}

    public EventOutBox(String topic, String payload, LocalDateTime timestamp) {
        this.topic = topic;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public static EventOutBox publish(String topic, String payload) {
        return new EventOutBox(topic, payload, LocalDateTime.now());
    }
}
