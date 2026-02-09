package com.omnitrack.dto;

import java.time.LocalDateTime;

/**
 * DTO = Data Transfer Object
 * Object envoyé par le réseau (JSON)
 * On expose uniquement les champs nécessaires (pas le clientId)
 */
public class ParcoursDTO {

    private Long id;
    private String journeyType;
    private String status;
    private String channel;
    private LocalDateTime createdAt;
    private LocalDateTime interruptedAt;
    private LocalDateTime resumedAt;
    private LocalDateTime completedAt;

    /* constructeur vide */
    public ParcoursDTO() {}

    /* constructeur avec tous les champs */
    public ParcoursDTO(Long id, String journeyType, String status, String channel, 
                       LocalDateTime createdAt, LocalDateTime interruptedAt, 
                       LocalDateTime resumedAt, LocalDateTime completedAt) {
        this.id = id;
        this.journeyType = journeyType;
        this.status = status;
        this.channel = channel;
        this.createdAt = createdAt;
        this.interruptedAt = interruptedAt;
        this.resumedAt = resumedAt;
        this.completedAt = completedAt;
    }

    /* getters et setters */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJourneyType() { return journeyType; }
    public void setJourneyType(String journeyType) { this.journeyType = journeyType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getInterruptedAt() { return interruptedAt; }
    public void setInterruptedAt(LocalDateTime interruptedAt) { this.interruptedAt = interruptedAt; }

    public LocalDateTime getResumedAt() { return resumedAt; }
    public void setResumedAt(LocalDateTime resumedAt) { this.resumedAt = resumedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    
}
