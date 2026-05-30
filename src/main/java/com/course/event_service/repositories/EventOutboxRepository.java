package com.course.event_service.repositories;

import com.course.event_service.entities.EventOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventOutboxRepository extends JpaRepository<EventOutbox, UUID> {
}
