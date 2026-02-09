package com.omnitrack.controller;

import com.omnitrack.dto.ParcoursDTO;
import com.omnitrack.service.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcours")
public class ParcoursController {

    @Autowired
    private ParcoursService parcoursService;

    @GetMapping
    public List<ParcoursDTO> getAllParcours() {
        return parcoursService.getAllParcours();
    }


    @GetMapping("/status/{status}")
    public List<ParcoursDTO> getParcoursByStatus(@PathVariable String status) {
        return parcoursService.getParcoursByStatus(status);
    }

    /* GET /api/parcours/{id} - Récupérer un parcours par ID */
    @GetMapping("/{id}")
    public ParcoursDTO getParcoursById(@PathVariable Long id) {
        return parcoursService.getParcoursById(id);
    }

    /* GET /api/parcours/client/{clientId} - Filtrer par client */
    @GetMapping("/client/{clientId}")
    public List<ParcoursDTO> getParcoursByClient(@PathVariable String clientId) {
        return parcoursService.getParcoursByClientId(clientId);
    }

    /* GET /api/parcours/type/{journeyType} - Filtrer par journeyType */
    @GetMapping("/type/{journeyType}")
    public List<ParcoursDTO> getParcoursByJourneyType(@PathVariable String journeyType) {
        return parcoursService.getParcoursByJourneyType(journeyType);
    }

    /* GET /api/parcours/channel/{channel} - Filtrer par channel */
    @GetMapping("/channel/{channel}")
    public List<ParcoursDTO> getParcoursByChannel(@PathVariable String channel) {
        return parcoursService.getParcoursByChannel(channel);
    }

    /* POST /api/parcours - Créer un parcours */
    @PostMapping
    public ParcoursDTO createParcours(@RequestBody ParcoursDTO parcoursDTO) {
        return parcoursService.createParcours(parcoursDTO);
    }

    /* PUT /api/parcours/{id}/interrupt - Interrompre un parcours */
    @PutMapping("/{id}/interrupt")
    public ParcoursDTO interruptParcours(@PathVariable Long id) {
        return parcoursService.interruptParcours(id);
    }

    /* PUT /api/parcours/{id}/resume - Reprendre un parcours */
    @PutMapping("/{id}/resume")
    public ParcoursDTO resumeParcours(@PathVariable Long id) {
        return parcoursService.resumeParcours(id);
    }

    /* PUT /api/parcours/{id}/complete - Terminer un parcours */
    @PutMapping("/{id}/complete")
    public ParcoursDTO completeParcours(@PathVariable Long id) {
        return parcoursService.completeParcours(id);
    }
}