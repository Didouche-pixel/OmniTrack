package com.omnitrack.service;

import com.omnitrack.client.BackendApiClient;
import com.omnitrack.dto.ParcoursDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcoursService {

    @Autowired
    private BackendApiClient backendApiClient;



        /* GET - Récupérer tous les parcours */
    public List<ParcoursDTO> getAllParcours() {
        return backendApiClient.getAllParcours();
    }

    /* GET - Récupérer un parcours par ID */
    public ParcoursDTO getParcoursById(Long id) {
        return backendApiClient.getParcoursById(id);
    }

    /* GET - Récupérer les parcours par client */
    public List<ParcoursDTO> getParcoursByClientId(String clientId) {
        return backendApiClient.getParcoursByClient(clientId);
    }

    /* GET - Récupérer les parcours par status */
    public List<ParcoursDTO> getParcoursByStatus(String status) {
        return backendApiClient.getParcoursByStatus(status);
    }

    /* GET - Récupérer les parcours par journeyType */
    public List<ParcoursDTO> getParcoursByJourneyType(String journeyType) {
        return backendApiClient.getParcoursByJourneyType(journeyType);
    }

    /* GET - Récupérer les parcours par channel */
    public List<ParcoursDTO> getParcoursByChannel(String channel) {
        return backendApiClient.getParcoursByChannel(channel);
    }

    /* POST - Créer un parcours */
    public ParcoursDTO createParcours(ParcoursDTO parcoursDTO) {
        return backendApiClient.createParcours(parcoursDTO);
    }

    /* PUT - Interrompre un parcours */
    public ParcoursDTO interruptParcours(Long id) {
        return backendApiClient.interruptParcours(id);
    }

    /* PUT - Reprendre un parcours */
    public ParcoursDTO resumeParcours(Long id) {
        return backendApiClient.resumeParcours(id);
    }

    /* PUT - Terminer un parcours */
    public ParcoursDTO completeParcours(Long id) {
        return backendApiClient.completeParcours(id);
    }

}