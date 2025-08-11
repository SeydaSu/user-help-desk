package com.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.admin.client.TicketClient;
import com.admin.dto.TicketAdminUpdateRequest;
import com.admin.dto.TicketResponse;
import com.admin.service.impl.AdminTicketResponseService;

public class AdminTicketResponseServiceTest {
    @Mock
    private TicketClient ticketClient;

    @InjectMocks
    private AdminTicketResponseService adminTicketResponseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidRequest_whenRespondToTicket_thenReturnsOk() {
        Long ticketId = 1L;
        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest();
        request.setResponse("Test response");

        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setId(ticketId);
        expectedResponse.setResponse("Test response");

        when(ticketClient.respondToTicket(ticketId, request)).thenReturn(expectedResponse);

        TicketResponse actualResponse = adminTicketResponseService.respondToTicket(ticketId, request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getResponse(), actualResponse.getResponse());

        verify(ticketClient, times(1)).respondToTicket(ticketId, request);
    }



    @Test
    void givenInvalidRequest_whenRespondToTicket_thenThrowsInvalidInputException() {
        Long ticketId = 2L;
        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId,  1L, 1L, null, 1L, 1L);

        when(ticketClient.respondToTicket(eq(ticketId), eq(request)))
                .thenThrow(new RuntimeException("Response can not be null or empty"));

        assertThrows(RuntimeException.class, () -> adminTicketResponseService.respondToTicket(ticketId, request));

        verify(ticketClient, times(1)).respondToTicket(ticketId, request);
    }

     @Test
    void givenValidRequest_whenUpdateTag_thenReturnsOk() {
        Long ticketId = 2L;

        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId, 1L, 1L, "response content", 1L, 1L);

        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setTagId(1L);

        when(ticketClient.updateTag(eq(ticketId), eq(request)))
                .thenReturn(expectedResponse);

        TicketResponse actualResponse = adminTicketResponseService.updateTicketTag(ticketId, request);

        assertEquals(expectedResponse.getTagId(), actualResponse.getTagId());
        verify(ticketClient, times(1)).updateTag(ticketId, request);
    }

    @Test
    void givenInvalidRequest_whenUpdateTag_thenThrowsException() {
         Long ticketId = 2L;
        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId,  1L, 1L, "response content", null, 1L);

        when(ticketClient.updateTag(eq(ticketId), eq(request)))
                .thenThrow(new RuntimeException("Tag can not be null or empty"));

        assertThrows(RuntimeException.class, () -> adminTicketResponseService.updateTicketTag(ticketId, request));

        verify(ticketClient, times(1)).updateTag(ticketId, request);
    }

    


    @Test
    void givenValidRequest_whenUpdatePriority_thenReturnsOk() {
        Long ticketId = 2L;

        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId, 1L, 1L, "response content", 1L, 1L);

        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setPriorityId(1L);

        when(ticketClient.updatePriority(eq(ticketId), eq(request)))
                .thenReturn(expectedResponse);;

        TicketResponse actualResponse = adminTicketResponseService.updateTicketPriority(ticketId, request);

        assertEquals(expectedResponse.getPriorityId(), actualResponse.getPriorityId());
        verify(ticketClient, times(1)).updatePriority(ticketId, request);
    }

    @Test
    void givenInvalidRequest_whenUpdatePriority_thenThrowsException() {
        Long ticketId = 2L;
        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId,  1L,null, "response content", 1L, 1L);

        when(ticketClient.updatePriority(eq(ticketId), eq(request)))
                .thenThrow(new RuntimeException("Priority can not be null or empty"));

        assertThrows(RuntimeException.class, () -> adminTicketResponseService.updateTicketPriority(ticketId, request));

        verify(ticketClient, times(1)).updatePriority(ticketId, request);
    }


    @Test
    void givenValidRequest_whenUpdateStatus_thenReturnsOk() {
        Long ticketId = 2L;

        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId, 1L, 1L, "response content", 1L, 1L);

        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setStatusId(1L);

        when(ticketClient.updateStatus(eq(ticketId), eq(request)))
                .thenReturn(expectedResponse);;

        TicketResponse actualResponse = adminTicketResponseService.updateTicketStatus(ticketId, request);

        assertEquals(expectedResponse.getStatusId(), actualResponse.getStatusId());
        verify(ticketClient, times(1)).updateStatus(ticketId, request);
    }

    @Test
    void givenInvalidRequest_whenUpdateStatus_thenThrowsException() {
        Long ticketId = 2L;
        TicketAdminUpdateRequest request = new TicketAdminUpdateRequest(ticketId,  null, 1L, "response content", 1L, 1L);

        when(ticketClient.updateStatus(eq(ticketId), eq(request)))
                .thenThrow(new RuntimeException("Status can not be null or empty"));

        assertThrows(RuntimeException.class, () -> adminTicketResponseService.updateTicketStatus(ticketId, request));

        verify(ticketClient, times(1)).updateStatus(ticketId, request);
    }





    
}
