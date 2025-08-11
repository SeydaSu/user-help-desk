package com.ticket.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.ticket.config.JwtService;
import com.ticket.dto.TicketResponse;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketQueryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketQueryService implements ITicketQueryService {

    private final TicketRepository ticketRepository;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    @Override
    public List<TicketEntity> getTicketsByCurrentUser() {
        String token = extractTokenFromHeader();

        if (token == null || !jwtService.isTokenValid(token)) {
            throw new TicketNotFoundException("Invalid or missing token");
        }

        String userIdStr = jwtService.extractUserId(token);

        if (userIdStr == null || userIdStr.isBlank()) {
            throw new TicketNotFoundException("User ID not found in token");
        }

        Long userId = Long.parseLong(userIdStr);
        List<TicketEntity> tickets = ticketRepository.findByUserId(userId);

        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("No tickets found for userId: " + userId);
        }

        return tickets;
    }

    private String extractTokenFromHeader() {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
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
