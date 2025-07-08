package com.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.TicketStatusEntity;

public interface TicketStatusRepository extends JpaRepository<TicketStatusEntity, Long> {


}
