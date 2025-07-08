package com.ticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.TicketEntity;

public interface TicketRepository  extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findById(Long id);

}
