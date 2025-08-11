package com.ticket.service.impl;

import org.springframework.stereotype.Service;
import com.ticket.client.TagClient;

import com.ticket.dto.TagResponse;
import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
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
    private final JwtService jwtService;

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
