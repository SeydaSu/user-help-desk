package com.ticket.service.impl;

import org.springframework.stereotype.Service;

import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.service.ITicketCreateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketCreateService implements ITicketCreateService {

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        
    }

}
