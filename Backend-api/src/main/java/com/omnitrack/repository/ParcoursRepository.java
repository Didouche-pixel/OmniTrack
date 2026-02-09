package com.omnitrack.repository;


import com.omnitrack.entity.ParcoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParcoursRepository extends JpaRepository<ParcoursEntity,Long> {
    /**
     * Trouver tous les parcours d'un client
     * SQL généré : SELECT * FROM parcours WHERE client_id = ?
     */
    List<ParcoursEntity> findByClientId(String clientId);

    /**
     * Trouver les parcours par statut (en cours, interrompu, finis)
     */
    List<ParcoursEntity> findByStatus(String status);

    /**
     * Trouver les parcours par type
     */
    List<ParcoursEntity> findByJourneyType(String journeyType);

    /**
     * Trouver les parcours par canal (WEB, MOBILE)
     */
    List<ParcoursEntity> findByChannel(String channel);


    /**
     *trouver le parcour avec l'id du client et avec son status 
     */

   @Query("SELECT p FROM ParcoursEntity p WHERE p.clientId = :clientId AND p.status = :status")
        List<ParcoursEntity> findByClientIdAndStatus(
            @Param("clientId") String clientId, 
            @Param("status") String status
        );
}