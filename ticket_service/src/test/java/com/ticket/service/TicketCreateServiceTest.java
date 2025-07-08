package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketCreateService;

public class TicketCreateServiceTest {

    @Test
    public void givenValidTicketRequest_whenCreateTicket_thenReturnsSavedTicketResponse() {
        TicketEntity ticket1 = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                //.statusId(1L)
                //.priorityId(1L)
                .createdBy("user1")
                .tagId(1L)
                .userId(1L)
                .build();
        
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket1);

        TicketCreateService ticketService = new TicketCreateService(ticketRepository);
        TicketResponse response = ticketService.createTicket(new TicketRequest("Ticket 1", "Description for ticket 1", 1L, 1L, 1L));

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));

    }



    @Test
    public void givenNullOrEmptyTicketDetails_whenCreateTicket_thenThrowsInvalidInputException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        TicketCreateService ticketService = new TicketCreateService(ticketRepository);

        TicketRequest nullRequest = new TicketRequest(null, null, 1L, 1L, 1L);
        TicketRequest emptyRequest = new TicketRequest("", "", 1L, 1L, 1L);

        assertThrows(TicketNotFoundException.class, () -> ticketService.createTicket(emptyRequest) );
        assertThrows(TicketNotFoundException.class, () -> ticketService.createTicket(nullRequest) );

        verify(ticketRepository, never()).save(any(TicketEntity.class));

    }

    

}
