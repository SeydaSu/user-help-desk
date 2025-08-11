package com.ticket.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTicketCreatedEvent(String eventJson) {
        kafkaTemplate.send("ticketCreated", eventJson);
    }

    public void sendTicketUpdatedEvent(String eventJson) {
        kafkaTemplate.send("ticketUpdated", eventJson);
    }

    
}

