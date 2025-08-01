package com.project.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserRegisteredEvent(String eventJson) {
        kafkaTemplate.send("userRegistered", eventJson);
    }

    public void sendUserLoginEvent(String eventJson) {
        kafkaTemplate.send("userLogin", eventJson);
    }
}

