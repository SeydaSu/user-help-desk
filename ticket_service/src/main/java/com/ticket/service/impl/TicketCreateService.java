package com.ticket.service.impl;

import org.springframework.stereotype.Service;

import com.ticket.client.AdminClient;
import com.ticket.client.PriorityEntity;
import com.ticket.client.StatusEntity;
import com.ticket.client.TagClient;
import com.ticket.dto.PriorityResponse;
import com.ticket.dto.StatusResponse;
import com.ticket.dto.TagResponse;
import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.exception.TagNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketCreateService;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketCreateService implements ITicketCreateService {

    @Autowired
    private final TagClient tagClient; // feign client

    @Autowired
    private final AdminClient adminClient;
    
    @Autowired
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        String createdBy = "unknown";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            createdBy = authentication.getName();
        }

        // 1. Tag var mı kontrol et
        TagResponse tag = tagClient.getTagById(request.getTagId());

        // 2. Eğer tag null veya exception fırlatırsa burada try-catch ile handle
        if (tag == null) {
            throw new TagNotFoundException("Geçersiz tag ID: " + request.getTagId());
        }

        List<StatusEntity> status = adminClient.getAllStatuses();
        List<PriorityEntity> priority = adminClient.getAllPriorities();

        // 3. Ticket oluştur
        TicketEntity ticket = TicketEntity.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .tagId(tag.getId()) // valid olduğu teyit edildi
            .createdBy(createdBy) // JWT'den alınır
            .statusId(status.get(0).getId()) // valid olduğu teyit edildi
            .priorityId(priority.get(0).getId()) 
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
