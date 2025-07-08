package com.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.TicketPriorityEntity;

public interface TicketPriorityRepository extends JpaRepository<TicketPriorityEntity, Long> {
    
    // Additional query methods can be defined here if needed

}
