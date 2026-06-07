package com.tickets.event_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tickets.event_service.dto.CreateEventRequest;
import com.tickets.event_service.entity.Event;
import com.tickets.event_service.exception.InsufficientInventoryException;
import com.tickets.event_service.infrastructure.web.exception.GlobalExceptionHandler;
import com.tickets.event_service.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventController - Unit Tests")
class EventControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private EventService service;

    @InjectMocks
    private EventController controller;

    private Event event;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        event = new Event(
                "Roberto Carlos Show",
                LocalDateTime.of(2025, 12, 25, 20, 0),
                new BigDecimal("150.00"),
                100
        );
    }

    // -------------------------------------------------------------------------
    // POST /events
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("POST /events: should return 201 Created with the created event")
    void create_shouldReturn201() throws Exception {
        CreateEventRequest req = new CreateEventRequest(
                "Roberto Carlos Show",
                LocalDateTime.of(2025, 12, 25, 20, 0),
                new BigDecimal("150.00"),
                100
        );

        when(service.create(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Roberto Carlos Show"))
                .andExpect(jsonPath("$.availableQuantity").value(100));
    }

    // -------------------------------------------------------------------------
    // GET /events
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /events: should return 200 with event list")
    void findAll_shouldReturn200WithList() throws Exception {
        Event other = new Event("Festival", LocalDateTime.now(), new BigDecimal("200.00"), 50);
        when(service.findAll()).thenReturn(List.of(event, other));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Roberto Carlos Show"));
    }

    @Test
    @DisplayName("GET /events: should return empty list when there are no events")
    void findAll_shouldReturnEmptyList() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // -------------------------------------------------------------------------
    // GET /events/{id}
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("GET /events/{id}: should return 200 with the event")
    void findById_shouldReturn200() throws Exception {
        when(service.findById(1L)).thenReturn(event);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Roberto Carlos Show"))
                .andExpect(jsonPath("$.price").value(150.00));
    }

    @Test
    @DisplayName("GET /events/{id}: should return 404 when event does not exist")
    void findById_shouldReturn404WhenNotFound() throws Exception {
        when(service.findById(99L)).thenThrow(new RuntimeException("Event not found: 99"));

        mockMvc.perform(get("/events/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Event not found: 99"));
    }

    // -------------------------------------------------------------------------
    // PATCH /events/{id}/quantity
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("PATCH /events/{id}/quantity: should return 200 with updated quantity")
    void updateQuantity_shouldReturn200() throws Exception {
        event.setAvailableQuantity(50);
        when(service.updateQuantity(eq(1L), eq(50))).thenReturn(event);

        mockMvc.perform(patch("/events/1/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("quantity", 50))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(50));
    }

    // -------------------------------------------------------------------------
    // PATCH /events/{id}/price
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("PATCH /events/{id}/price: should return 200 with updated price")
    void updatePrice_shouldReturn200() throws Exception {
        event.setPrice(new BigDecimal("299.99"));
        when(service.updatePrice(eq(1L), any(BigDecimal.class))).thenReturn(event);

        mockMvc.perform(patch("/events/1/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("price", 299.99))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(299.99));
    }

    // -------------------------------------------------------------------------
    // POST /events/{id}/reserve
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("POST /events/{id}/reserve: should return 200 when reservation is successful")
    void reserve_shouldReturn200() throws Exception {
        doNothing().when(service).reserveTicket(1L);

        mockMvc.perform(post("/events/1/reserve"))
                .andExpect(status().isOk());

        verify(service, times(1)).reserveTicket(1L);
    }

    @Test
    @DisplayName("POST /events/{id}/reserve: should return 409 when no tickets are available")
    void reserve_shouldReturn409WhenOutOfStock() throws Exception {
        doThrow(new InsufficientInventoryException(1L)).when(service).reserveTicket(1L);

        mockMvc.perform(post("/events/1/reserve"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("No tickets available for event: 1"));
    }
}