package com.log.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;  // ticket-service, user-service ...

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;

    private LocalDateTime timestamp;

    
    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }


}
