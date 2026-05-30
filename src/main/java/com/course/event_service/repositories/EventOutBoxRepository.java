package com.course.event_service.repositories;

import com.course.event_service.entities.EventOutBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventOutBoxRepository extends JpaRepository<EventOutBox, UUID> {
}
