package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ticket.config.JwtService;
import com.ticket.dto.TicketResponse;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketQueryService;

import jakarta.servlet.http.HttpServletRequest;

public class TicketQueryServiceTest {

    private MockedStatic<SecurityContextHolder> mockedSecurityContext;

    @AfterEach
    void tearDown() {
        if (mockedSecurityContext != null) {
            mockedSecurityContext.close();
        }
    }

    @Test
    public void givenExistingTickets_whenGetTicketsByCurrentUser_thenReturnsTicketResponseList() {
        TicketEntity ticket1 = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                .statusId(1L)
                .priorityId(1L)
                .createdBy("user1")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketEntity ticket2 = TicketEntity.builder()
                .title("Ticket 2")
                .description("Description for ticket 2")
                .statusId(2L)
                .priorityId(2L)
                .createdBy("user1")
                .tagId(2L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        mockedSecurityContext = mockStatic(SecurityContextHolder.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);

        when(request.getHeader("Authorization")).thenReturn("Bearer mock-token");
        when(jwtService.isTokenValid("mock-token")).thenReturn(true);
        when(jwtService.extractUserId("mock-token")).thenReturn("1");
        when(ticketRepository.findByUserId(1L)).thenReturn(Arrays.asList(ticket1, ticket2));

        TicketQueryService ticketService = new TicketQueryService(ticketRepository, jwtService, request);
        List<TicketEntity> tickets = ticketService.getTicketsByCurrentUser();

        assertEquals(2, tickets.size());
        assertEquals("Ticket 1", tickets.get(0).getTitle());
        verify(ticketRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void givenNoTicketExist_whenGetTicketsByCurrentUser_thenThrowsException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        mockedSecurityContext = mockStatic(SecurityContextHolder.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);

        when(request.getHeader("Authorization")).thenReturn("Bearer mock-token");
        when(jwtService.extractUserId("mock-token")).thenReturn("1");
        when(jwtService.isTokenValid("mock-token")).thenReturn(true);
        when(ticketRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        TicketQueryService ticketService = new TicketQueryService(ticketRepository, jwtService, request);

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getTicketsByCurrentUser();
        });

        assertEquals("No tickets found for userId: 1", exception.getMessage());
        verify(ticketRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void givenExistingTicket_whenGetTicketById_thenReturnsTicketResponse() {
        TicketEntity ticket = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                .statusId(1L)
                .priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(ticket.getId())).thenReturn(java.util.Optional.of(ticket));
        JwtService jwtService = mock(JwtService.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        TicketQueryService ticketService = new TicketQueryService(ticketRepository, jwtService, request);
        TicketResponse response = ticketService.getTicketById(ticket.getId());

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).findById(ticket.getId());
    }

    @Test
    public void givenNonExistingTicket_whenGetTicketById_thenThrowsTicketNotFoundException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketQueryService ticketService = new TicketQueryService(ticketRepository, mock(JwtService.class),
                mock(HttpServletRequest.class));

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(1L));

        verify(ticketRepository, times(1)).findById(1L);
    }

}
