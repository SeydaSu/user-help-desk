package com.log.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.model.LogEntry;
import com.log.model.LogLevel;
import com.log.repository.LogRepository;
import com.log.service.ILogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService implements ILogService{

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = {"userRegistered", "userLogin", "ticketCreated", "priorityUpdated"}, groupId = "log-group")
    public void listen(ConsumerRecord<String, String> record) {
        String topic = record.topic();
        String message = record.value();

        LogEntry logEntry = new LogEntry();
        logEntry.setServiceName(topic);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            logEntry.setMessage(message);

            // eventType alanı varsa logLevel olarak set edelim (örnek)
            if (jsonNode.has("eventType")) {
                String eventType = jsonNode.get("eventType").asText();

                switch (eventType) {
                    case "USER_REGISTERED":
                    case "USER_LOGGED_IN":
                        logEntry.setLogLevel(LogLevel.INFO);
                        break;
                    case "PRIORITY_UPDATED":
                        logEntry.setLogLevel(LogLevel.WARN);
                        break;
                    default:
                        logEntry.setLogLevel(LogLevel.DEBUG);
                }
            } else {
                logEntry.setLogLevel(LogLevel.INFO);
            }

            // Eğer timestamp varsa, parse edip atayabilirsin:
            if (jsonNode.has("timestamp")) {
                String timestampStr = jsonNode.get("timestamp").asText();
                logEntry.setTimestamp(LocalDateTime.parse(timestampStr));
            } else {
                logEntry.setTimestamp(LocalDateTime.now());
            }

        } catch (Exception e) {
            // Parsing başarısızsa, mesajı olduğu gibi kaydet
            logEntry.setMessage(message);
            logEntry.setLogLevel(LogLevel.INFO);
            logEntry.setTimestamp(LocalDateTime.now());
        }

        logRepository.save(logEntry);
        System.out.println("Log kaydedildi: " + message);
    }




    @Override
    public List<LogEntry> getAllLogs() {
        return logRepository.findAll();
    }
}
