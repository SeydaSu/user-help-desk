package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ticket.dto.TicketResponse;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketQueryService;

public class TicketQueryServiceTest {

    private MockedStatic<SecurityContextHolder> mockedSecurityContext;

    @AfterEach
    void tearDown() {
        // MockedStatic'i her test sonrası kapat
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
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        when(ticketRepository.findByCreatedBy("user1")).thenReturn(Arrays.asList(ticket1, ticket2));

        mockedSecurityContext = mockStatic(SecurityContextHolder.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
            
        when(authentication.getName()).thenReturn("user1");
        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
            

        TicketQueryService ticketService = new TicketQueryService(ticketRepository);
        List<TicketEntity> tickets = ticketService.getTicketsByCurrentUser();

        assertEquals(2, tickets.size());
        assertEquals("Ticket 1", tickets.get(0).getTitle());
        verify(ticketRepository, times(1)).findByCreatedBy("user1");
    }

    @Test
    public void givenNoTicketExist_whenGetTicketsByCurrentUser_thenReturnsEmptyList() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
       
        mockedSecurityContext = mockStatic(SecurityContextHolder.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
            
        when(authentication.getName()).thenReturn("user1");
        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
            
        // Mock repository to return empty list
        when(ticketRepository.findByCreatedBy("user1")).thenReturn(Arrays.asList());
            
        TicketQueryService ticketService = new TicketQueryService(ticketRepository);
            
            // Bu durumda exception fırlatılması beklenir
        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getTicketsByCurrentUser();
        });
            
        assertEquals("No tickets found for user: user1", exception.getMessage());
        verify(ticketRepository, times(1)).findByCreatedBy("user1");
        
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

        TicketQueryService ticketService = new TicketQueryService(ticketRepository);
        TicketResponse response = ticketService.getTicketById(ticket.getId());

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).findById(ticket.getId());
    }

    @Test
    public void givenNonExistingTicket_whenGetTicketById_thenThrowsTicketNotFoundException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketQueryService ticketService = new TicketQueryService(ticketRepository);

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(1L));

        verify(ticketRepository, times(1)).findById(1L);
    }

}
