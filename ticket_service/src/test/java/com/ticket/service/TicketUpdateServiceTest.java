package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.dto.TicketUpdateRequest;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketUpdateService;

public class TicketUpdateServiceTest {
    
     @Test
    public void givenValidTicketUpdateRequest_whenUpdateTicket_thenReturnsUpdatedTicketResponse() {
        TicketEntity ticket = TicketEntity.builder()
                .id(1L)
                .title("Ticket 1")
                .description("Description for ticket 1")
                //.statusId(1L)
                //.priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket);

        TicketUpdateService ticketService = new TicketUpdateService(ticketRepository);
        TicketResponse response = ticketService.updateTicket(1L, new TicketUpdateRequest("Updated Title", "Updated Description", 1L, 1L, 1L));

        assertEquals("Updated Title", response.getTitle());
        assertEquals("Updated Description", response.getDescription());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonExistingTicket_whenUpdateTicket_thenThrowsTicketNotFoundException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketUpdateService ticketService = new TicketUpdateService(ticketRepository);

        assertThrows(TicketNotFoundException.class, () -> ticketService.updateTicket(1L, new TicketRequest("Updated Title", "Updated Description", 1L, 1L, 1L)));

        verify(ticketRepository, times(0)).save(any(TicketEntity.class));
    }

}
