package com.ticket.service.impl;

import org.springframework.stereotype.Service;
import com.ticket.client.TagClient;

import com.ticket.dto.TagResponse;
import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.exception.TagNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketCreateService;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketCreateService implements ITicketCreateService {

    @Autowired
    private final TagClient tagClient; // feign client
    
    @Autowired
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        String createdBy = "unknown";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            createdBy = authentication.getName();
        }

       
        // 3. Ticket oluştur
        TicketEntity ticket = TicketEntity.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .tagId(request.getTagId()) // valid olduğu teyit edildi
            .createdBy(createdBy) // JWT'den alınır
            .statusId(request.getStatusId()) // valid olduğu teyit edildi
            .priorityId(request.getPriorityId()) 
            .userId(request.getUserId()) 
            .build();

        ticketRepository.save(ticket);

        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .tagId(ticket.getTagId())
                .statusId(ticket.getStatusId())
                .priorityId(ticket.getPriorityId())
                .userId(ticket.getUserId())
                .createdBy(getCurrentUserId())
                .createdAt(ticket.getCreatedAt())
                .build();
    }



    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
