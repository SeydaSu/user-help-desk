package com.ticket.service;

import java.util.List;

import com.ticket.dto.TicketResponse;

public interface ITicketQueryService {

    List<TicketResponse> getTicketsByCurrentUser();

    List<TicketResponse> getTicketsById(Long ticketId);
}
