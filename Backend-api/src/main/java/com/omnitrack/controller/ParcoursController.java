package com.omnitrack.controller;

import com.omnitrack.dto.ParcoursDTO;
import com.omnitrack.entity.ParcoursEntity;
import com.omnitrack.service.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/parcours")
public class ParcoursController {
    

@Autowired
private ParcoursService parcoursService;

/* GET /api/parcours - recuperer tous les parcours */
@GetMapping
public List<ParcoursDTO> getAllParcours() {
    List<ParcoursEntity> entities = parcoursService.getAllParcours();
    
    // Convertir chaque Entity en DTO
    return entities.stream()
            .map(entity -> new ParcoursDTO(
                entity.getId(),
                entity.getJourneyType(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt(),
                entity.getInterruptedAt(),
                entity.getResumedAt(),
                entity.getCompletedAt()
            ))
            .collect(Collectors.toList());
}

/* GET /api/parcours/{id} - recuperer un parcours par ID */
@GetMapping("/{id}")
public ParcoursDTO getParcoursById(@PathVariable Long id) {
    // 1. Appeler le service
    ParcoursEntity entity = parcoursService.getParcoursById(id);
    
    // 2. Convertir Entity → DTO (un seul objet, pas besoin de stream)
    return new ParcoursDTO(
        entity.getId(),
        entity.getJourneyType(),
        entity.getStatus(),
        entity.getChannel(),
        entity.getCreatedAt(),
        entity.getInterruptedAt(),
        entity.getResumedAt(),
        entity.getCompletedAt()
    );
}


@PostMapping
public ParcoursDTO createParcours(@RequestBody ParcoursEntity request) { 

    ParcoursEntity entity = parcoursService.createParcours(request);

    return new ParcoursDTO(
        entity.getId(),
        entity.getJourneyType(),
        entity.getStatus(),
        entity.getChannel(),
        entity.getCreatedAt(),
        entity.getInterruptedAt(),
        entity.getResumedAt(),
        entity.getCompletedAt()
    );
 }

@GetMapping("/client/{clientId}")
public List<ParcoursDTO> getParcoursByClient(@PathVariable String clientId) { 


    List<ParcoursEntity> entities = parcoursService.getParcoursByClientId(clientId);

    
    // Convertir chaque Entity en DTO
    return entities.stream()
            .map(entity -> new ParcoursDTO(
                entity.getId(),
                entity.getJourneyType(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt(),
                entity.getInterruptedAt(),
                entity.getResumedAt(),
                entity.getCompletedAt()
            ))
            .collect(Collectors.toList());
 }

/* GET /api/parcours/status/{status} - filtrer par status */
@GetMapping("/status/{status}")
public List<ParcoursDTO> getParcoursByStatus(@PathVariable String status) {
    List<ParcoursEntity> entities = parcoursService.getParcoursByStatus(status);
    
    return entities.stream()
            .map(entity -> new ParcoursDTO(
                entity.getId(),
                entity.getJourneyType(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt(),
                entity.getInterruptedAt(),
                entity.getResumedAt(),
                entity.getCompletedAt()
            ))
            .collect(Collectors.toList());
}

/* GET /api/parcours/type/{journeyType} - filtrer par journeyType */
@GetMapping("/type/{journeyType}")
public List<ParcoursDTO> getParcoursByJourneyType(@PathVariable String journeyType) {
    List<ParcoursEntity> entities = parcoursService.getParcoursByJourneyType(journeyType);
    
    return entities.stream()
            .map(entity -> new ParcoursDTO(
                entity.getId(),
                entity.getJourneyType(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt(),
                entity.getInterruptedAt(),
                entity.getResumedAt(),
                entity.getCompletedAt()
            ))
            .collect(Collectors.toList());
}

/* GET /api/parcours/channel/{channel} - filtrer par channel */
@GetMapping("/channel/{channel}")
public List<ParcoursDTO> getParcoursByChannel(@PathVariable String channel) {
    List<ParcoursEntity> entities = parcoursService.getParcoursByChannel(channel);
    
    return entities.stream()
            .map(entity -> new ParcoursDTO(
                entity.getId(),
                entity.getJourneyType(),
                entity.getStatus(),
                entity.getChannel(),
                entity.getCreatedAt(),
                entity.getInterruptedAt(),
                entity.getResumedAt(),
                entity.getCompletedAt()
            ))
            .collect(Collectors.toList());
}

/* PUT /api/parcours/{id}/interrupt - interrompre un parcours */
@PutMapping("/{id}/interrupt")
public ParcoursDTO interruptParcours(@PathVariable Long id) {
    ParcoursEntity entity = parcoursService.interruptParcours(id);
    
    return new ParcoursDTO(
        entity.getId(),
        entity.getJourneyType(),
        entity.getStatus(),
        entity.getChannel(),
        entity.getCreatedAt(),
        entity.getInterruptedAt(),
        entity.getResumedAt(),
        entity.getCompletedAt()
    );
}

/* PUT /api/parcours/{id}/resume - reprendre un parcours */
@PutMapping("/{id}/resume")
public ParcoursDTO resumeParcours(@PathVariable Long id) {
    ParcoursEntity entity = parcoursService.resumeParcours(id);
    
    return new ParcoursDTO(
        entity.getId(),
        entity.getJourneyType(),
        entity.getStatus(),
        entity.getChannel(),
        entity.getCreatedAt(),
        entity.getInterruptedAt(),
        entity.getResumedAt(),
        entity.getCompletedAt()
    );
}

/* PUT /api/parcours/{id}/complete - terminer un parcours */
@PutMapping("/{id}/complete")
public ParcoursDTO completeParcours(@PathVariable Long id) {
    ParcoursEntity entity = parcoursService.completeParcours(id);
    
    return new ParcoursDTO(
        entity.getId(),
        entity.getJourneyType(),
        entity.getStatus(),
        entity.getChannel(),
        entity.getCreatedAt(),
        entity.getInterruptedAt(),
        entity.getResumedAt(),
        entity.getCompletedAt()
    );
}

}
