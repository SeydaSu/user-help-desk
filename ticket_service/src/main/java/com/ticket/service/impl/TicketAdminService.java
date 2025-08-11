package com.ticket.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.dto.TicketAdminUpdateRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.kafka.KafkaProducerService;
import com.ticket.model.TicketEntity;
import com.ticket.repository.TicketRepository;
import com.ticket.service.ITicketAdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketAdminService implements ITicketAdminService {

        @Autowired
        private final TicketRepository ticketRepository;
        private final KafkaProducerService kafkaProducerService;

        @Override
        public TicketResponse respondToTicket(Long ticketId, TicketAdminUpdateRequest request) {
                if (request == null || request.getResponse() == null || request.getResponse().isBlank()) {
                        throw new RuntimeException("Response content cannot be null or empty");
                }
                TicketEntity ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

                // Update the ticket response
                ticket.setResponse(request.getResponse());

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
                                .userId(updatedTicket.getUserId())
                                .createdAt(updatedTicket.getCreatedAt())
                                .updatedAt(updatedTicket.getUpdatedAt())
                                .response(updatedTicket.getResponse()) // Assuming response field exists
                                .build();

        }

        @Override
        public TicketResponse updatePriority(Long ticketId, TicketAdminUpdateRequest request) {
                TicketEntity ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

                if (request == null || request.getPriorityId() == null) {
                        throw new RuntimeException("Response content cannot be null or empty");
                }
                ticket.setPriorityId(request.getPriorityId());

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
                                .userId(updatedTicket.getUserId())
                                .createdAt(updatedTicket.getCreatedAt())
                                .updatedAt(updatedTicket.getUpdatedAt())
                                .response(updatedTicket.getResponse()) // Assuming response field exists
                                .build();
        }

        @Override
        public TicketResponse updateStatus(Long ticketId, TicketAdminUpdateRequest request) {
                TicketEntity ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

                if (request == null || request.getStatusId() == null) {
                        throw new RuntimeException("Response content cannot be null or empty");
                }
                // Update the ticket status
                ticket.setStatusId(request.getStatusId());

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
                                .userId(updatedTicket.getUserId())
                                .createdAt(updatedTicket.getCreatedAt())
                                .updatedAt(updatedTicket.getUpdatedAt())
                                .response(updatedTicket.getResponse()) // Assuming response field exists
                                .build();
        }

        @Override
        public TicketResponse updateTag(Long ticketId, TicketAdminUpdateRequest request) {
                TicketEntity ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

                if (request == null || request.getTagId() == null) {
                        throw new RuntimeException("Response content cannot be null or empty");
                }
                // Update the ticket tag
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
                                .userId(updatedTicket.getUserId())
                                .createdAt(updatedTicket.getCreatedAt())
                                .updatedAt(updatedTicket.getUpdatedAt())
                                .response(updatedTicket.getResponse()) // Assuming response field exists
                                .build();
        }

        @Override
        public List<TicketEntity> getAllTicketsForAdmin() {
                List<TicketEntity> tickets = ticketRepository.findAll();
                return tickets;
        }

}
