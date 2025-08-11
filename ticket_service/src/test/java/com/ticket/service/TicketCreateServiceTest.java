package com.ticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ticket.client.TagClient;
import com.ticket.client.AdminClient;
import com.ticket.client.PriorityEntity;
import com.ticket.client.StatusEntity;
import com.ticket.dto.TagResponse;
import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.impl.TicketCreateService;

public class TicketCreateServiceTest {

    @Test
    public void givenValidTicketRequest_whenCreateTicket_thenReturnsSavedTicketResponse() {
        
        TicketEntity ticket1 = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                .statusId(1L)
                .priorityId(1L) 
                .createdBy("user1")
                .tagId(1L)
                .userId(1L)
                .build();

        TestingAuthenticationToken auth = new TestingAuthenticationToken("user1", null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TagClient tagClient = mock(TagClient.class);
        AdminClient adminClient = mock(AdminClient.class);
        TicketRepository ticketRepository = mock(TicketRepository.class);

        StatusEntity status = new StatusEntity(1L, "Open", "user1");
        PriorityEntity priority = new PriorityEntity(1L, "High", "user2");

        List<StatusEntity> statusList = List.of(status);
        List<PriorityEntity> priorityList = List.of(priority);

        when(adminClient.getAllStatuses()).thenReturn(statusList);
        when(adminClient.getAllPriorities()).thenReturn(priorityList);


        when(tagClient.getTagById(1L)).thenReturn(new TagResponse(1L, "Tag 1", null, null));
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticket1);

        TicketCreateService ticketService = new TicketCreateService(tagClient, ticketRepository);


        TicketRequest request = new TicketRequest("Ticket 1", "Description for ticket 1", 1L, 1L, 1L, 1L);

        // 6. Method çağrısı ve sonuç doğrulama
        TicketResponse response = ticketService.createTicket(request);

        assertEquals("Ticket 1", response.getTitle());
        assertEquals("Description for ticket 1", response.getDescription());
        verify(ticketRepository, times(1)).save(any(TicketEntity.class));
    }




    @Test
    public void givenNullOrEmptyTicketDetails_whenCreateTicket_thenThrowsInvalidInputException() {
        TicketRepository ticketRepository = mock(TicketRepository.class);
        TagClient tagClient = mock(TagClient.class);

        TicketCreateService ticketService = new TicketCreateService(tagClient, ticketRepository);

        TicketRequest nullRequest = new TicketRequest(null, null, 1L, 1L, 1L, 1L);
        TicketRequest emptyRequest = new TicketRequest("", "", 1L, 1L, 1L, 1L);

        assertThrows(RuntimeException.class, () -> ticketService.createTicket(nullRequest));
        assertThrows(RuntimeException.class, () -> ticketService.createTicket(emptyRequest));

        verify(ticketRepository, never()).save(any(TicketEntity.class));
    }

    

}
