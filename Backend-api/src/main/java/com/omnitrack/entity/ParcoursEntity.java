
package com.omnitrack.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity              
@Table(name = "Parcours")
public class ParcoursEntity { 

    @Id                          // ← Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)    
    private String clientId;
    
    @Column
    private String parcourType; // ex: "OUVERTURE_COMPTE"
    
    @Column
    private String status; // en cours , interrompu , finis  

    @Column
    private String cannel; // mobile / web 

    @Column
    private LocalDateTime tempcreationparcour ; //le moment de la creation du parcour 

    @Column
    private LocalDateTime tempinterruptionparcour ; // le moment d'intterrution du parcour

     @Column
    private LocalDateTime temprepriseparcour ; // le moment de la reprise du parcour 

    @Column
    private LocalDateTime tempfinisparcour ; // la fin du parcour

    public ParcoursEntity() {}

    public ParcoursEntity(String clientId ,String parcourType ,String status,String cannel){
        this.cannel=cannel;
        this.clientId=clientId;
        this.parcourType=parcourType;
        this.status=status;
        this.tempcreationparcour=LocalDateTime.now();
    }




    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getJourneyType() { return parcourType; }
    public void setJourneyType(String journeyType) { this.parcourType = journeyType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getChannel() { return cannel; }
    public void setChannel(String channel) { this.cannel = channel; }

    public LocalDateTime getCreatedAt() { return tempcreationparcour; }
    public void setCreatedAt(LocalDateTime createdAt) { this.tempcreationparcour = createdAt; }

    public LocalDateTime getInterruptedAt() { return tempinterruptionparcour; }
    public void setInterruptedAt(LocalDateTime interruptedAt) { this.tempinterruptionparcour = interruptedAt; }

    public LocalDateTime getResumedAt() { return temprepriseparcour; }
    public void setResumedAt(LocalDateTime resumedAt) { this.temprepriseparcour = resumedAt; }

    public LocalDateTime getCompletedAt() { return tempfinisparcour; }
    public void setCompletedAt(LocalDateTime completedAt) { this.tempfinisparcour = completedAt; }



    
}