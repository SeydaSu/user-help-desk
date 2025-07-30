package com.admin.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.admin.client.TicketClient;
import com.admin.dto.TicketResponse;
import com.admin.service.IAdminTicketListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminTicketListService implements IAdminTicketListService{

    private final TicketClient ticketClient;

    @Override
    public List<TicketResponse> getAllTickets() {
        List<TicketResponse> tickets = ticketClient.getAllTicketsForAdmin();
        return tickets;
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        TicketResponse ticket = ticketClient.getTicketById(id);
        return ticket;
    }

    
}
