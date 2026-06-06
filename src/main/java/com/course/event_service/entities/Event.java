package com.course.event_service.entities;

import com.course.event_service.entities.types.Category;
import com.course.event_service.entities.types.Modality;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    @Column(nullable = false)
    private LocalDateTime endDateTime;
    private String venue;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Modality modality;
    @Column(nullable = false)
    private UUID creatorId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private Boolean isCanceled;

    public Event() {}

    public Event(String name, String description, LocalDateTime startDateTime, LocalDateTime endDateTime,
                 String venue, Category category, Modality modality, UUID creatorId, LocalDateTime createdAt,
                 Boolean isCanceled) {
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.venue = venue;
        this.category = category;
        this.modality = modality;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.isCanceled = isCanceled;
    }

    public UUID getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled() {
        this.isCanceled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Event create(String name, String description, LocalDateTime startDateTime, LocalDateTime endDateTime,
                               String venue, Category category, Modality modality, UUID creatorId) {
        return new Event(name, description, startDateTime, endDateTime, venue, category, modality, creatorId,
                LocalDateTime.now(), Boolean.FALSE);
    }
}