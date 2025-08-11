package com.admin.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTagCreatedEvent(String eventJson) {
        kafkaTemplate.send("tagCreated", eventJson);
    }

    public void sendPriorityCreatedvent(String eventJson) {
        kafkaTemplate.send("priorityCreated", eventJson);
    }

    public void sendStatusCreatedvent(String eventJson) {
        kafkaTemplate.send("statusCreated", eventJson);
    }


    
}

