package com.ticket.service;

import java.util.List;


import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;

public interface ITicketQueryService {

    List<TicketEntity> getTicketsByCurrentUser();

    TicketResponse getTicketById(Long ticketId);
}
