package com.log.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;


import com.log.model.LogEntry;
import com.log.model.LogLevel;
import com.log.repository.LogRepository;


class LogServiceTests {

	
	@Test
	public void givenLogsExist_whenGetAllLogs_thenReturnAllLogs() {
    	LogEntry log1 = new LogEntry();
		log1.setServiceName("user-service");
		log1.setMessage("Kullanıcı kaydı başarılı.");
		log1.setLogLevel(LogLevel.INFO);
		log1.setTimestamp(LocalDateTime.now());

		LogEntry log2 = new LogEntry();
		log2.setServiceName("ticket-service");
		log2.setMessage("Bilet oluşturuldu.");
		log2.setLogLevel(LogLevel.WARN);
		log2.setTimestamp(LocalDateTime.now());
        LogRepository repo = mock(LogRepository.class);
        when(repo.findAll()).thenReturn(Arrays.asList(log1, log2));

        LogService service = new LogService(repo);
        List<LogEntry> result = service.getAllLogs();

        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
	}

	@Test
	public void givenNoLogs_whenGetAllLogs_thenReturnEmptyList() {
    	LogRepository repo = mock(LogRepository.class);
        when(repo.findAll()).thenReturn(Arrays.asList());

        LogService service = new LogService(repo);
        List<LogEntry> result = service.getAllLogs();

        assertEquals(0, result.size());
        verify(repo, times(1)).findAll();
	}


	@Test
    void listen_shouldSaveLogEntry_whenValidJsonMessage() {
        
        LogRepository repo = mock(LogRepository.class);
        LogService service = new LogService(repo);

        String json = "{\"eventType\":\"USER_REGISTERED\",\"message\":\"Kullanıcı kaydı başarılı.\",\"timestamp\":\"2025-07-03T12:00:00\"}";
        ConsumerRecord<String, String> record = new ConsumerRecord<>("userRegistered", 0, 0L, null, json);

        service.listen(record);

        verify(repo, times(1)).save(any(LogEntry.class));
    }

    @Test
    void listen_shouldSaveLogEntry_whenInvalidJsonMessage() {
        LogRepository repo = mock(LogRepository.class);
        LogService service = new LogService(repo);

        String invalidJson = "bu bir json değil";
        ConsumerRecord<String, String> record = new ConsumerRecord<>("userRegistered", 0, 0L, null, invalidJson);

        service.listen(record);

        verify(repo, times(1)).save(any(LogEntry.class));
    }
}
