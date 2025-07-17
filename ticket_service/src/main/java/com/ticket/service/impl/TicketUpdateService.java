package com.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.dto.TicketResponse;
import com.ticket.dto.TicketUpdateRequest;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketUpdateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketUpdateService implements ITicketUpdateService{

    @Autowired
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse updateTicket(Long ticketId, TicketUpdateRequest request) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + ticketId));

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatusId(request.getStatusId());
        ticket.setPriorityId(request.getPriorityId());
        ticket.setTagId(request.getTagId());

        TicketEntity updatedTicket = ticketRepository.save(ticket);

        return TicketResponse.builder()
                .id(updatedTicket.getId())
                .title(updatedTicket.getTitle())
                .description(updatedTicket.getDescription())
                .statusId(updatedTicket.getStatusId())
                .priorityId(updatedTicket.getPriorityId())
                .tagId(updatedTicket.getTagId())
                .createdBy(updatedTicket.getCreatedBy())
                .userId(updatedTicket.getUserId())
                .build();
    }

}
