package com.ticket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.TicketResponse;
import com.ticket.dto.TicketUpdateRequest;
import com.ticket.service.ITicketUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TicketUpdateController {

    private final ITicketUpdateService ticketUpdateService;

    @PostMapping("ticket/{id}")
    public TicketResponse updateTicket(Long id, TicketUpdateRequest request) {
        return ticketUpdateService.updateTicket(id, request);
    }

}
