package com.log.controller.impl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.log.model.LogEntry;
import com.log.model.LogLevel;
import com.log.service.ILogService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LogController.class, excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
    org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})

@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = LogController.class)
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ILogService logService;

    @Test
    void givenLogsExist_whenGetAllLogs_thenReturnAllLogs() throws Exception {
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

        List<LogEntry> logs = Arrays.asList(log1, log2);

        when(logService.getAllLogs()).thenReturn(logs);

        mockMvc.perform(get("/api/v1/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}