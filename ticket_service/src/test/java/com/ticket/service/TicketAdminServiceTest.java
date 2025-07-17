package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.ticket.dto.TicketAdminUpdateRequest;
import com.ticket.dto.TicketResponse;

import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketAdminService;

public class TicketAdminServiceTest {


      @Test
    public void givenValidTicketAdminUpdateRequest_whenAdminUpdateTicket_thenReturnsUpdatedTicketResponse() {
        // Given
        TicketEntity originalTicket = TicketEntity.builder()
                .id(1L)
                .title("Original Title")
                .description("Original Description")
                .statusId(1L)
                .priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();
        
        TicketEntity updatedTicket = TicketEntity.builder()
                .id(1L)
                .title("Updated Title")
                .description("Updated Description")
                .statusId(2L)
                .priorityId(2L)
                .createdBy("user")
                .tagId(2L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(originalTicket));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(updatedTicket);

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);
        TicketAdminUpdateRequest updateRequest = new TicketAdminUpdateRequest(2L, 2L, 2L, "Updated Response", 2L);

        // When
        TicketResponse response = ticketService.respondToTicket(1L, updateRequest);

        // Then
        assertEquals("Updated Title", response.getTitle());
        assertEquals("Updated Description", response.getDescription());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonExistingTicket_whenAdminUpdateTicket_thenThrowsTicketNotFoundException() {  
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        assertThrows(RuntimeException.class, () -> ticketService.respondToTicket(1L, new TicketAdminUpdateRequest(2L,1L,null, "Updated Response", 1L)));

        verify(ticketRepository, times(0)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonValidTicketAdminUpdateRequest_whenAdminUpdateTicket_thenThrowsInvalidInputException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        TicketAdminUpdateRequest invalidRequest = new TicketAdminUpdateRequest(null, null, null, "Updated Response", 1L);

        assertThrows(RuntimeException.class, () -> ticketService.respondToTicket(1L, invalidRequest));

        verify(ticketRepository, never()).save(any(TicketEntity.class));
    }


    @Test
    public void givenValidTicketAdminRequest_whenUpdatePriority_thenReturnsUpdatedTicketResponse() {
        TicketEntity ticket = TicketEntity.builder()
                .id(1L)
                .title("Ticket 1")
                .description("Description for ticket 1")
                .statusId(1L)
                .priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket);

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);
        TicketResponse response = ticketService.updatePriority(1L,new TicketAdminUpdateRequest(2L, 1L, 2L, "Updated Response", 1L));

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonExistingTicket_whenUpdatePriority_thenThrowsTicketNotFoundException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        assertThrows(RuntimeException.class, () -> ticketService.updatePriority(1L, new TicketAdminUpdateRequest(1L, 1L, 2L, "Updated Response", 1L)));

        verify(ticketRepository, times(0)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonValidTicketAdminRequest_whenUpdatePriority_thenThrowsInvalidInputException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        TicketAdminUpdateRequest invalidRequest = new TicketAdminUpdateRequest(null, null, null, "Updated Response", 1L);

        assertThrows(RuntimeException.class, () -> ticketService.updatePriority(1L, new TicketAdminUpdateRequest(1L, 1L, 2L, "Updated Response", 1L)));

        verify(ticketRepository, never()).save(any(TicketEntity.class));
    }


    @Test
    public void givenValidTicketAdminRequest_whenUpdateStatus_thenReturnsUpdatedTicketResponse() {
        TicketEntity ticket = TicketEntity.builder()
                .id(1L)
                .title("Ticket 1")
                .description("Description for ticket 1")
                .statusId(1L)
                .priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket);

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);
        TicketResponse response = ticketService.updateStatus(1L, new TicketAdminUpdateRequest(1L, 2L, 1L, "Updated Response", 1L));

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }


    @Test
    public void givenNonExistingTicket_whenUpdateStatus_thenThrowsTicketNotFoundException() {   
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        assertThrows(RuntimeException.class, () -> ticketService.updateStatus(1L, new TicketAdminUpdateRequest(1L, 2L, 1L, "Updated Response", 1L)));

        verify(ticketRepository, times(0)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonValidTicketAdminRequest_whenUpdateStatus_thenThrowsInvalidInputException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        TicketAdminUpdateRequest invalidRequest = new TicketAdminUpdateRequest(null, null, null, "Updated Response", 1L);

        assertThrows(RuntimeException.class, () -> ticketService.updateStatus(1L, new TicketAdminUpdateRequest(1L, 2L, 1L, "Updated Response", 1L)));

        verify(ticketRepository, never()).save(any(TicketEntity.class));
    }
    
    @Test
    public void givenValidTicketAdminRequest_whenUpdateTag_thenReturnsUpdatedTicketResponse() {
        TicketEntity ticket = TicketEntity.builder()
                .id(1L)
                .title("Ticket 1")
                .description("Description for ticket 1")
                .statusId(1L)
                .priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket);

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);
        TicketResponse response = ticketService.updateTag(1L, new TicketAdminUpdateRequest(1L, 1L, 1L, "Updated Response", 2L));

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }

    @Test
    public void givenNonExistingTicket_whenUpdateTag_thenThrowsTicketNotFoundException() {  
        TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        TicketAdminService ticketService = new TicketAdminService(ticketRepository);

        assertThrows(RuntimeException.class, () -> ticketService.updateTag(1L, new TicketAdminUpdateRequest(1L, 1L, 1L, "Updated Response", 2L)));

        verify(ticketRepository, times(0)).save(any(TicketEntity.class));
    }
}
