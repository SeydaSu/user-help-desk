package com.ticket.service;

import org.junit.jupiter.api.Test;

import com.ticket.dto.TicketRequest;
import com.ticket.model.TicketPriorityEntity;

public class TicketServiceTest {
    
    @Test
    public void givenValidTicketRequest_whenCreateTicket_thenReturnsSavedTicketResponse() {
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setTitle("Test Ticket");
        ticketRequest.setDescription("This is a test ticket.");
        ticketRequest.setUserId(1L);
        ticketRequest.setPriority(TicketPriorityEntity.MEDIUM);
        ticketRequest.setStatus("Open");
        
    }

    @Test
    public void givenNullOrEmptyTicketDetails_whenCreateTicket_thenThrowsInvalidInputException() {
        // Test implementation here
    }
}
