package com.log.kafka;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import com.log.service.ILogService;

public class LogKafkaConsumerTest {

    @Test
    void consume_shouldCallLogServiceListen() {
        ILogService logService = mock(ILogService.class);
        LogKafkaConsumer consumer = new LogKafkaConsumer(logService);

        ConsumerRecord<String, String> record = new ConsumerRecord<>("userRegistered", 0, 0L, null, "{\"eventType\":\"USER_REGISTERED\"}");

        consumer.consume(record);

        verify(logService, times(1)).listen(record);
    }
    
}
