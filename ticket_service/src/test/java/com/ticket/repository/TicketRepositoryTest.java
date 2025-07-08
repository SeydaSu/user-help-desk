package com.ticket.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ticket.model.TicketEntity;
import com.ticket.model.TicketPriorityEntity;
import com.ticket.model.TicketStatusEntity;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    @BeforeEach
    public void cleanUp() {
        ticketRepository.deleteAll();
    }
    

    @Test
    public void TicketRepository_GetAllTickets_ReturnsAllTickets() {
        TicketStatusEntity ticketStatusEntity
                = TicketStatusEntity.builder()
                .name("Open")
                .build();
        TicketPriorityEntity ticketPriorityEntity
                = TicketPriorityEntity.builder()
                .name("High")
                .build();
                
        // Save the referenced entities FIRST
        ticketStatusRepository.save(ticketStatusEntity);
        ticketPriorityRepository.save(ticketPriorityEntity);

        TicketEntity ticket1 = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                .status(ticketStatusEntity)
                .priority(ticketPriorityEntity)
                .createdBy("user1")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketEntity ticket2 = TicketEntity.builder()
                .title("Ticket 2")  
                .description("Description for ticket 2")
                .status(ticketStatusEntity)
                .priority(ticketPriorityEntity)
                .createdBy("user2")
                .tagId(2L)
                .userId(2L)
                .build();

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

        List<TicketEntity> tickets = ticketRepository.findAll();
        assertEquals(2, tickets.size());
    }


    @Test
    public void TicketRepository_FindById_ReturnsTicket() {
        TicketStatusEntity TicketStatusEntity
                = com.ticket.model.TicketStatusEntity.builder()
                .name("Open")
                .build();
        TicketPriorityEntity TicketPriorityEntity
                = com.ticket.model.TicketPriorityEntity.builder()
                .name("High")
                .build();

        TicketEntity ticket = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                .status(TicketStatusEntity)
                .priority(TicketPriorityEntity)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        ticketRepository.save(ticket);

        TicketEntity foundTicket = ticketRepository.findById(ticket.getId()).orElse(null);
        assertEquals(ticket.getTitle(), foundTicket.getTitle());
    }
    
}
