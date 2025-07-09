package com.ticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.dto.TicketAdminUpdateRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketAdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketAdminService implements ITicketAdminService  {

    @Autowired
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse respondToTicket(Long ticketId, TicketAdminUpdateRequest request) {
       TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        // Update the ticket response
        ticket.setResponse(request.getResponse());
        

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
                .response(updatedTicket.getResponse()) // Assuming response field exists
                .build();
    }

    @Override
    public TicketResponse updatePriority(Long ticketId, TicketAdminUpdateRequest request) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        
        ticket.setPriorityId(request.getPriorityId());

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
                .response(updatedTicket.getResponse()) // Assuming response field exists
                .build();
    }

    @Override
    public TicketResponse updateStatus(Long ticketId, TicketAdminUpdateRequest request) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        // Update the ticket status
        ticket.setStatusId(request.getStatusId());

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
                .response(updatedTicket.getResponse()) // Assuming response field exists
                .build();
    }

    @Override
    public TicketResponse updateTag(Long ticketId, TicketAdminUpdateRequest request) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        // Update the ticket tag
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
                .response(updatedTicket.getResponse()) // Assuming response field exists
                .build();
    }
    
}
