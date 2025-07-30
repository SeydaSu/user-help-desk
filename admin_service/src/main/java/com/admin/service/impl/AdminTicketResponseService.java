package com.admin.service.impl;

import org.springframework.stereotype.Service;

import com.admin.client.TicketClient;
import com.admin.dto.TicketAdminUpdateRequest;
import com.admin.dto.TicketResponse;
import com.admin.service.IAdminTicketResponseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminTicketResponseService implements IAdminTicketResponseService{

    private final TicketClient ticketClient;

    @Override
    public TicketResponse respondToTicket(Long id, TicketAdminUpdateRequest request) {
        TicketResponse ticket = ticketClient.respondToTicket(id,request);
        return ticket;
    }

    @Override
    public TicketResponse updateTicketPriority(Long id, TicketAdminUpdateRequest request) {
        TicketResponse ticket = ticketClient.updatePriority(id,request);
        return ticket;
    }

    @Override
    public TicketResponse updateTicketStatus(Long id, TicketAdminUpdateRequest request) {
        TicketResponse ticket = ticketClient.updateStatus(id,request);
        return ticket;
    }

    @Override
    public TicketResponse updateTicketTag(Long id, TicketAdminUpdateRequest request) {
        TicketResponse ticket = ticketClient.updateTag(id,request);
        return ticket;
    }
    
}
