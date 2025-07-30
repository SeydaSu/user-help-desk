package com.admin.service;

import com.admin.client.TicketClient;
import com.admin.dto.TicketResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.admin.service.impl.AdminTicketListService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminTicketListServiceTest {

    @Mock
    private TicketClient ticketClient;

    @Mock
    private AdminTicketListService adminTicketListService;

    @BeforeEach
    void setUp() {
        ticketClient = mock(TicketClient.class);
        adminTicketListService = new AdminTicketListService(ticketClient);
    }

    @Test
    void givenExistingTicket_whenGetAllTicketsForAdmin_thenReturnsTicketResponse() {
        
        TicketResponse ticket1 = new TicketResponse(
            1L,
            "Subject 1",
            "Description 1",
            "response content",
            2L,
            2L,
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now(),
            "user1",
            5L,
            10L
        );

        TicketResponse ticket2 = new TicketResponse(
            2L,
            "Subject 2",
            "Description 2",
            "response content",
            1L,
            2L,
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now(),
            "user2",
            4L,
            5L
        );

        List<TicketResponse> mockTickets = List.of(ticket1, ticket2);

        when(ticketClient.getAllTicketsForAdmin()).thenReturn(mockTickets);

        List<TicketResponse> result = adminTicketListService.getAllTickets();

        assertEquals(2, result.size());
        assertEquals("Subject 1", result.get(0).getTitle());
        verify(ticketClient, times(1)).getAllTicketsForAdmin();
    }

    @Test
    void givenNoExistingTicket_whenGetAllTicketsForAdmin_thenReturnsTicketResponse() {
        List<TicketResponse> mockTickets = Collections.EMPTY_LIST;
        when(ticketClient.getAllTicketsForAdmin()).thenReturn(mockTickets);
        List<TicketResponse> result = adminTicketListService.getAllTickets();
        
        assertEquals(0, result.size());
        verify(ticketClient, times(1)).getAllTicketsForAdmin();
         


    }

    @Test
    void givenExistingTicket_whenGetTicketById_thenReturnsResponse() {
        Long ticketId = 1L;
        TicketResponse mockTicket = new TicketResponse(
            ticketId, 
            "Subject 1",
            "Description 1",
            "response content",
            2L,
            2L,
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now(),
            "user1",
            5L,
            10L
        );

        when(ticketClient.getTicketById(ticketId)).thenReturn(mockTicket);

        TicketResponse result = adminTicketListService.getTicketById(ticketId);

        assertNotNull(result);
        assertEquals(ticketId, result.getId());
        assertEquals("Subject 1", result.getTitle());
        verify(ticketClient, times(1)).getTicketById(ticketId);
    }


    @Test
void givenNoExistingTicket_whenGetTicketById_thenReturnsNull() {
    Long ticketId = 1L;

    when(ticketClient.getTicketById(ticketId)).thenReturn(null);

    TicketResponse result = adminTicketListService.getTicketById(ticketId);

    assertNull(result);
    verify(ticketClient, times(1)).getTicketById(ticketId);
}

}
