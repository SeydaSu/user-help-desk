package com.log.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.log.model.LogEntry;

public interface ILogService {
    
    public void listen(ConsumerRecord<String, String> record);
    
    public List<LogEntry> getAllLogs();
}
