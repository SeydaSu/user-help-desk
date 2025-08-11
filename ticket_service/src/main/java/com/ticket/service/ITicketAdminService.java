package com.ticket.service;

import java.util.List;

import com.ticket.dto.TicketAdminUpdateRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;

public interface ITicketAdminService {

    TicketResponse respondToTicket(Long ticketId, TicketAdminUpdateRequest request);

    TicketResponse updatePriority(Long ticketId, TicketAdminUpdateRequest request);

    TicketResponse updateStatus(Long ticketId, TicketAdminUpdateRequest request);

    TicketResponse updateTag(Long ticketId, TicketAdminUpdateRequest request);

    List<TicketEntity> getAllTicketsForAdmin();

}
