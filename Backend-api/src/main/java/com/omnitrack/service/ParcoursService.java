package com.omnitrack.service;

import com.omnitrack.entity.ParcoursEntity;
import com.omnitrack.repository.ParcoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcoursService {
    
    @Autowired 
    private ParcoursRepository parcoursRepository;

    /* recuperer tous les parcours 
     */
    public List<ParcoursEntity> getAllParcours() {
        return parcoursRepository.findAll();
    }
    /* recuperer le parcous de l'utilisateur qui passe en param
     */
    public List<ParcoursEntity> getParcoursByClientId(String id) {
        return parcoursRepository.findByClientId(id);
    }

    /* recupere un parcour avec son id 
    */
    @SuppressWarnings("null")
    public ParcoursEntity getParcoursById(Long id) {
        return parcoursRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parcours introuvable : " + id));
    }

    /* recuperer les parcours par statut (en cours, interrompu, finis) */
    public List<ParcoursEntity> getParcoursByStatus(String status) {
        return parcoursRepository.findByStatus(status);
    }

    /* recuperer les parcours par canal (WEB, MOBILE) */
    public List<ParcoursEntity> getParcoursByChannel(String cannel) {
        return parcoursRepository.findByChannel(cannel);
    }

    /* recuperer les parcours par type */
    public List<ParcoursEntity> getParcoursByJourneyType(String parcoursType) {
        return parcoursRepository.findByJourneyType(parcoursType);
    }

    /* recuperer les parcours par clientId et status */
    public List<ParcoursEntity> getParcoursByClientIdAndStatus(String clientId, String status) {
        return parcoursRepository.findByClientIdAndStatus(clientId, status);
    }

    /* creer un nouveau parcours */
    @SuppressWarnings("null")
    public ParcoursEntity createParcours(ParcoursEntity parcours) {
        return parcoursRepository.save(parcours);
    }

    /* interrompre un parcours */
    public ParcoursEntity interruptParcours(Long id) {
        ParcoursEntity parcours = getParcoursById(id);
        parcours.setStatus("INTERRUPTED");
        parcours.setInterruptedAt(java.time.LocalDateTime.now());
        return parcoursRepository.save(parcours);
    }

    /* reprendre un parcours */
    public ParcoursEntity resumeParcours(Long id) {
        ParcoursEntity parcours = getParcoursById(id);
        parcours.setStatus("ACTIVE");
        parcours.setResumedAt(java.time.LocalDateTime.now());
        return parcoursRepository.save(parcours);
    }

    /* finaliser un parcours */
    public ParcoursEntity completeParcours(Long id) {
        ParcoursEntity parcours = getParcoursById(id);
        parcours.setStatus("COMPLETED");
        parcours.setCompletedAt(java.time.LocalDateTime.now());
        return parcoursRepository.save(parcours);
    }


}
