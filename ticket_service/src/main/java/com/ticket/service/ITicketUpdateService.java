package com.ticket.service;


import com.ticket.dto.TicketResponse;
import com.ticket.dto.TicketUpdateRequest;

public interface ITicketUpdateService {

    TicketResponse updateTicket(Long ticketId, TicketUpdateRequest request);
    
}
