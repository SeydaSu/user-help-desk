package com.log.kafka;

import com.log.service.ILogService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LogKafkaConsumer {

    private final ILogService logService;

    @KafkaListener(topics = {"userRegistered", "userLogin", "ticketCreated","ticketUpdated", "tagCreated", "priorityCreated", "statusCreated"}, groupId = "log-group")
    public void consume(ConsumerRecord<String, String> record) {
        logService.listen(record);
    }
}