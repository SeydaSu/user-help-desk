package com.ticket.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ticket.dto.TicketResponse;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketQueryService implements ITicketQueryService {

    private final TicketRepository ticketRepository;
    
    @Override
    public List<TicketEntity> getTicketsByCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Eğer authentication yoksa veya authenticated değilse exception fırlat
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            throw new TicketNotFoundException("User is not authenticated");
        }
        
        String currentUsername = auth.getName();
        List<TicketEntity> tickets = ticketRepository.findByCreatedBy(currentUsername);
        
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("No tickets found for user: " + currentUsername);
        }
        
        return tickets;
    }


    @Override
    public TicketResponse getTicketById(Long ticketId) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        return TicketResponse.builder()
            .id(ticket.getId())
            .title(ticket.getTitle())
            .description(ticket.getDescription())
            .tagId(ticket.getTagId())
            .statusId(ticket.getStatusId())
            .priorityId(ticket.getPriorityId())
            .userId(ticket.getUserId())
            .createdBy(ticket.getCreatedBy())
            .createdAt(ticket.getCreatedAt())
            .build();
    }


}
