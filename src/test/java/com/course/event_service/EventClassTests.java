package com.course.event_service;

import com.course.event_service.entities.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventClassTests {

    @Test
    void shouldCreateEventWithConstructor() {
        Event event = new Event(null, "Concert", "A music concert");

        assertEquals("Concert", event.getName());
        assertEquals("A music concert", event.getDescription());
        assertNull(event.getId()); // ID is null before persisting
    }

    @Test
    void shouldSetAndGetFields() {
        Event event = new Event();
        event.setId("1");
        event.setName("Festival");
        event.setDescription("A big festival");

        assertEquals("1", event.getId());
        assertEquals("Festival", event.getName());
        assertEquals("A big festival", event.getDescription());
    }

    @Test
    void shouldBeEqualWhenSameId() {
        Event event1 = new Event(null, "Concert", "Description");
        Event event2 = new Event(null, "Festival", "Other description");

        event1.setId("1");
        event2.setId("1");

        assertEquals(event1, event2); // same ID = equal
    }

    @Test
    void shouldNotBeEqualWhenDifferentId() {
        Event event1 = new Event(null, "Concert", "Description");
        Event event2 = new Event(null, "Concert", "Description");

        event1.setId("1");
        event2.setId("2");

        assertNotEquals(event1, event2);
    }

    @Test
    void shouldHaveSameHashCodeForSameId() {
        Event event1 = new Event(null, "Concert", "Description");
        Event event2 = new Event(null, "Festival", "Other");

        event1.setId("1");
        event2.setId("1");

        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void shouldNotEqualNull() {
        Event event = new Event(null, "Concert", "Description");
        assertNotEquals(null, event);
    }
}
