package com.ticket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.TicketEntity;

public interface TicketRepository  extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByCreatedBy(String currentUsername);

    List<TicketEntity> findByUserId(Long userId);
}
