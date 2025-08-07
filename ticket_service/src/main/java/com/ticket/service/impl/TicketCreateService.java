package com.ticket.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import com.ticket.client.TagClient;
import com.ticket.config.JwtAuthenticationToken;
import com.ticket.config.JwtService;
import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketCreateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketCreateService implements ITicketCreateService {

    private final TagClient tagClient;
    private final TicketRepository ticketRepository;
    private final JwtService jwtService;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank() ||
                request.getDescription() == null || request.getDescription().isBlank()) {
            throw new RuntimeException("Title and Description cannot be null or empty");
        }

        UserInfo currentUser = getCurrentUserInfo();

        System.out.println("Creating ticket with user: " + currentUser.getId() + " - " + currentUser.getName());

        TicketEntity ticket = TicketEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .tagId(request.getTagId())
                .createdBy(currentUser.getName())
                .statusId(request.getStatusId())
                .priorityId(request.getPriorityId())
                .userId(currentUser.getId())
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
                .createdBy(currentUser.getName())
                .createdAt(ticket.getCreatedAt())
                .build();
    }

    private UserInfo getCurrentUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Long userId = Long.parseLong(jwtAuth.getUserId());
            String userName = jwtAuth.getUserName();

            return new UserInfo(userId, userName);
        }

        return new UserInfo(-1L, "Anonymous");
    }

    private String getCurrentJwtToken() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        } catch (Exception e) {
            System.out.println("Could not get JWT token from request: " + e.getMessage());
        }
        return null;
    }

    private Long parseUserId(String userIdStr) {
        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Failed to parse userId: " + userIdStr);
            return null;
        }
    }

    public static class UserInfo {
        private final Long id;
        private final String name;

        public UserInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
