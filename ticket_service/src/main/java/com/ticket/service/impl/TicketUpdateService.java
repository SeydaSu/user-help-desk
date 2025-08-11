package com.ticket.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.dto.TicketResponse;
import com.ticket.dto.TicketUpdateRequest;
import com.ticket.exception.TicketNotFoundException;
import com.ticket.kafka.KafkaProducerService;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketUpdateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketUpdateService implements ITicketUpdateService{

    @Autowired
    private final TicketRepository ticketRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public TicketResponse updateTicket(Long ticketId, TicketUpdateRequest request) {
        TicketEntity ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + ticketId));

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatusId(request.getStatusId());
        ticket.setPriorityId(request.getPriorityId());
        ticket.setTagId(request.getTagId());

        TicketEntity updatedTicket = ticketRepository.save(ticket);

        
         String eventJson = """
                    {
                        "ticketId": "%s",
                        "title": "%s",
                        "createdBy": "%s",
                        "eventType": "TICKET_CREATED",
                        "timestamp": "%s"
                    }
                """.formatted(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getCreatedBy(),
                LocalDateTime.now());

        kafkaProducerService.sendTicketUpdatedEvent(eventJson);

        return TicketResponse.builder()
                .id(updatedTicket.getId())
                .title(updatedTicket.getTitle())
                .description(updatedTicket.getDescription())
                .statusId(updatedTicket.getStatusId())
                .priorityId(updatedTicket.getPriorityId())
                .tagId(updatedTicket.getTagId())
                .createdBy(updatedTicket.getCreatedBy())
                .createdAt(updatedTicket.getCreatedAt())
                .updatedAt(updatedTicket.getUpdatedAt())
                .userId(updatedTicket.getUserId())
                .build();
    }

}
