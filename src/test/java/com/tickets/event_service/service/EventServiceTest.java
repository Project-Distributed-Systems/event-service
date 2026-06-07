package com.tickets.event_service.service;

import com.tickets.event_service.entity.Event;
import com.tickets.event_service.exception.InsufficientInventoryException;
import com.tickets.event_service.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventService - Unit Tests")
class EventServiceTest {

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService service;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(
                "Roberto Carlos Show",
                LocalDateTime.of(2025, 12, 25, 20, 0),
                new BigDecimal("150.00"),
                100
        );
    }

    // -------------------------------------------------------------------------
    // create
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("create: should save and return the created event")
    void create_shouldReturnSavedEvent() {
        when(repository.save(any(Event.class))).thenReturn(event);

        Event result = service.create(event);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Roberto Carlos Show");
        verify(repository, times(1)).save(event);
    }

    // -------------------------------------------------------------------------
    // findAll
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("findAll: should return a list with all events")
    void findAll_shouldReturnEventList() {
        Event other = new Event("Festival", LocalDateTime.now(), new BigDecimal("200.00"), 50);
        when(repository.findAll()).thenReturn(List.of(event, other));

        List<Event> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll: should return empty list when there are no events")
    void findAll_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<Event> result = service.findAll();

        assertThat(result).isEmpty();
    }

    // -------------------------------------------------------------------------
    // findById
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("findById: should return event when ID exists")
    void findById_shouldReturnEvent() {
        when(repository.findById(1L)).thenReturn(Optional.of(event));

        Event result = service.findById(1L);

        assertThat(result.getName()).isEqualTo("Roberto Carlos Show");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById: should throw RuntimeException when ID does not exist")
    void findById_shouldThrowExceptionWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Event not found: 99");
    }

    // -------------------------------------------------------------------------
    // updateQuantity
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updateQuantity: should update and return event with new quantity")
    void updateQuantity_shouldUpdateQuantity() {
        when(repository.findById(1L)).thenReturn(Optional.of(event));
        when(repository.save(any(Event.class))).thenReturn(event);

        Event result = service.updateQuantity(1L, 50);

        assertThat(result.getAvailableQuantity()).isEqualTo(50);
        verify(repository).save(event);
    }

    @Test
    @DisplayName("updateQuantity: should throw exception when event does not exist")
    void updateQuantity_shouldThrowExceptionWhenEventNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateQuantity(99L, 10))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Event not found: 99");
    }

    // -------------------------------------------------------------------------
    // updatePrice
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updatePrice: should update and return event with new price")
    void updatePrice_shouldUpdatePrice() {
        BigDecimal newPrice = new BigDecimal("299.99");
        when(repository.findById(1L)).thenReturn(Optional.of(event));
        when(repository.save(any(Event.class))).thenReturn(event);

        Event result = service.updatePrice(1L, newPrice);

        assertThat(result.getPrice()).isEqualByComparingTo(newPrice);
        verify(repository).save(event);
    }

    // -------------------------------------------------------------------------
    // reserveTicket
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("reserveTicket: should decrement quantity when tickets are available")
    void reserveTicket_shouldDecrementQuantity() {
        when(repository.decrementQuantity(1L)).thenReturn(1);

        assertThatCode(() -> service.reserveTicket(1L)).doesNotThrowAnyException();
        verify(repository, times(1)).decrementQuantity(1L);
    }

    @Test
    @DisplayName("reserveTicket: should throw InsufficientInventoryException when no tickets available")
    void reserveTicket_shouldThrowExceptionWhenOutOfStock() {
        when(repository.decrementQuantity(1L)).thenReturn(0);

        assertThatThrownBy(() -> service.reserveTicket(1L))
                .isInstanceOf(InsufficientInventoryException.class)
                .hasMessageContaining("No tickets available for event: 1");
    }
}