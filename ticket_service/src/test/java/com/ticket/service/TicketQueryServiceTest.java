package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ticket.dto.TicketResponse;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketQueryService;

public class TicketQueryServiceTest {
    
    @Test
    public void givenExistingTickets_whenGetTicketsByCurrentUser_thenReturnsTicketResponseList() {
        TicketEntity ticket1 = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                //.statusId(1L)
                //.priorityId(1L)
                .createdBy("user1")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketEntity ticket2 = TicketEntity.builder()
                .title("Ticket 2")  
                .description("Description for ticket 2")
                //.statusId(2L)
                //.priorityId(2L)
                .createdBy("user2")
                .tagId(2L)
                .userId(2L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        TicketQueryService ticketService = new TicketQueryService(ticketRepository);
        List<TicketResponse> tickets = ticketService.getTicketsByCurrentUser("user1");

        assertEquals(1, tickets.size());
        assertEquals("Ticket 1", tickets.get(0).getTitle());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void givenNoTicketExist_whenGetTicketsByCurrentUser_thenReturnsEmptyList(){
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findAll()).thenReturn(Arrays.asList());

        TicketQueryService ticketService = new TickeQuerytService(ticketRepository);
        List<TicketResponse> tickets = ticketService.getTicketsByCurrentUser("user1");

        assertEquals(0, tickets.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void givenExistingTicket_whenGetTicketById_thenReturnsTicketResponse() {
        TicketEntity ticket = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                //.statusId(1L)
                //.priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(ticket.getId())).thenReturn(java.util.Optional.of(ticket));

        TicketQueryService ticketService = new TicketQueryService(ticketRepository);
        TicketResponse response = ticketService.getTicketById(ticket.getId());

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).findById(ticket.getId());
    }

    @Test
    public void givenNonExistingTicket_whenGetTicketById_thenThrowsTicketNotFoundException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketQueryService ticketService = new TicketQueryService(ticketRepository);

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(1L));

        verify(ticketRepository, times(1)).findById(1L);
    }

}
