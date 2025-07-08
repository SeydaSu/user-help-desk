package com.ticket.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(length = 1000)
    private String response;
    
    // Priority bilgisi (admin-service tarafından belirlenir)
    @Column(name = "priority_id", nullable = false)
    private Long priorityId;

    // Status bilgisi (admin-service tarafından belirlenir)
    @Column(name = "status_id", nullable = false)
    private Long statusId;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "user_id", nullable = false)
    private Long userId; // sadece ID tutulur, ilişkisel yapı kurulmaz

    @Column(name = "tag_id", nullable = false)
    private Long tagId; // sadece ID tutulur, ilişkisel yapı kurulmaz




    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
