package com.ticket.service;

import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;

public interface ITicketCreateService {

    TicketResponse createTicket(TicketRequest request);
    
}
