package com.omnitrack.dto;

import java.time.LocalDateTime;

public class ParcoursDTO {
    private Long id;
    private String journeyType;
    private String status;
    private String channel;
    private LocalDateTime createdAt;
    private LocalDateTime interruptedAt;
    private LocalDateTime resumedAt;
    private LocalDateTime completedAt;

    // Constructeur vide (OBLIGATOIRE pour Jackson - désérialisation JSON)
    public ParcoursDTO() {}

    // Constructeur avec tous les paramètres
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

    // Getters
    public Long getId() { return id; }
    public String getJourneyType() { return journeyType; }
    public String getStatus() { return status; }
    public String getChannel() { return channel; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getInterruptedAt() { return interruptedAt; }
    public LocalDateTime getResumedAt() { return resumedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setJourneyType(String journeyType) { this.journeyType = journeyType; }
    public void setStatus(String status) { this.status = status; }
    public void setChannel(String channel) { this.channel = channel; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setInterruptedAt(LocalDateTime interruptedAt) { this.interruptedAt = interruptedAt; }
    public void setResumedAt(LocalDateTime resumedAt) { this.resumedAt = resumedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}