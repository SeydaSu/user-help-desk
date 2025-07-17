package com.ticket.service;

import com.ticket.dto.TicketAdminUpdateRequest;
import com.ticket.dto.TicketResponse;

public interface ITicketAdminService {

    TicketResponse respondToTicket(Long ticketId, TicketAdminUpdateRequest request);

    TicketResponse updatePriority(Long ticketId, TicketAdminUpdateRequest request);

    TicketResponse updateStatus(Long ticketId, TicketAdminUpdateRequest request);

    TicketResponse updateTag(Long ticketId, TicketAdminUpdateRequest request);

}
