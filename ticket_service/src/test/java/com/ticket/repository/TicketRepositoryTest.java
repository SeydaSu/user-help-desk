package com.ticket.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ticket.model.TicketEntity;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;


    @BeforeEach
    public void cleanUp() {
        ticketRepository.deleteAll();
    }
    

    @Test
    public void TicketRepository_GetAllTickets_ReturnsAllTickets() {
 

        TicketEntity ticket1 = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                //.statusId(1L)
                //.priorityId(1L)
                .createdBy("user1")
                .tagId(1L)
                .userId(1L)
                .build();

        TicketEntity ticket2 = TicketEntity.builder()
                .title("Ticket 2")  
                .description("Description for ticket 2")
                //.statusId(2L)
                //.priorityId(2L)
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

        TicketEntity ticket = TicketEntity.builder()
                .title("Ticket 1")
                .description("Description for ticket 1")
                //.statusId(1L)
                //.priorityId(1L)
                .createdBy("user")
                .tagId(1L)
                .userId(1L)
                .build();

        ticketRepository.save(ticket);

        TicketEntity foundTicket = ticketRepository.findById(ticket.getId()).orElse(null);
        assertEquals(ticket.getTitle(), foundTicket.getTitle());
    }
    
}
