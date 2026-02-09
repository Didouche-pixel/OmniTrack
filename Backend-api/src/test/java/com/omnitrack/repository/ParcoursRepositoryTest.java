package com.omnitrack.repository;

import com.omnitrack.entity.ParcoursEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests d'intégration pour ParcoursRepository
 * 
 * POURQUOI ? Vérifier que les requêtes de base de données fonctionnent
 * correctement avec une vraie BD (ou une BD de test embarquée).
 * 
 * COMMENT ? En utilisant @DataJpaTest qui lance une BD de test H2
 * (ou PostgreSQL en test avec testcontainers).
 * 
 * IMPORTANT : Ces tests sont plus lents que les tests unitaires
 * mais plus fiables pour les requêtes SQL.
 */
@DataJpaTest
@ActiveProfiles("test")  // Utilise application-test.properties si elle existe
@DisplayName("Tests de ParcoursRepository - Accès aux données")
class ParcoursRepositoryTest {

    // ========== SETUP ==========
    /**
     * @Autowired : Injecte le vrai Repository avec une BD H2
     */
    @Autowired
    private ParcoursRepository parcoursRepository;

    private ParcoursEntity testParcours1;
    private ParcoursEntity testParcours2;
    private ParcoursEntity testParcours3;

    @BeforeEach
    void setUp() {
        // Créer des parcours de test
        testParcours1 = new ParcoursEntity("CLIENT001", "CREDIT", "EN_COURS", "WEB");
        testParcours1.setCreatedAt(LocalDateTime.of(2026, 2, 9, 10, 0));

        testParcours2 = new ParcoursEntity("CLIENT001", "EPARGNE", "COMPLETED", "MOBILE");
        testParcours2.setCreatedAt(LocalDateTime.of(2026, 1, 15, 14, 30));
        testParcours2.setCompletedAt(LocalDateTime.of(2026, 2, 8, 16, 45));

        testParcours3 = new ParcoursEntity("CLIENT002", "CREDIT", "EN_COURS", "WEB");
        testParcours3.setCreatedAt(LocalDateTime.of(2026, 2, 8, 9, 0));

        // Sauvegarder dans la BD de test
        parcoursRepository.saveAll(List.of(testParcours1, testParcours2, testParcours3));
    }

    // ========== TEST 1: SAVE & FIND BY ID ==========
    @Test
    @DisplayName("✅ Doit sauvegarder et récupérer un parcours")
    void testSaveAndFindById_Success() {
        // ACT : Créer et sauvegarder
        ParcoursEntity newParcours = new ParcoursEntity("CLIENT003", "ASSURANCE", "EN_COURS", "AGENCE");
        newParcours.setCreatedAt(LocalDateTime.now());
        ParcoursEntity saved = parcoursRepository.save(newParcours);

        // ASSERT : Vérifier le résultat
        assertNotNull(saved.getId(), "L'ID généré ne doit pas être null");
        
        // ACT : Récupérer
        Optional<ParcoursEntity> found = parcoursRepository.findById(saved.getId());

        // ASSERT
        assertTrue(found.isPresent(), "Le parcours doit exister");
        assertEquals("CLIENT003", found.get().getClientId());
        assertEquals("ASSURANCE", found.get().getJourneyType());
    }

    // ========== TEST 2: FIND BY CLIENT ID ==========
    @Test
    @DisplayName("✅ Doit retourner tous les parcours d'un client")
    void testFindByClientId_Success() {
        // ACT
        List<ParcoursEntity> clientParcours = parcoursRepository.findByClientId("CLIENT001");

        // ASSERT
        assertEquals(2, clientParcours.size(), "CLIENT001 doit avoir 2 parcours");
        assertTrue(clientParcours.stream().allMatch(p -> "CLIENT001".equals(p.getClientId())));
        
        // Vérifier les types de parcours
        assertTrue(clientParcours.stream().anyMatch(p -> "CREDIT".equals(p.getJourneyType())));
        assertTrue(clientParcours.stream().anyMatch(p -> "EPARGNE".equals(p.getJourneyType())));
    }

    // ========== TEST 3: FIND BY CLIENT ID - EMPTY ==========
    @Test
    @DisplayName("✅ Doit retourner liste vide si client inexistant")
    void testFindByClientId_Empty() {
        // ACT
        List<ParcoursEntity> result = parcoursRepository.findByClientId("CLIENT999");

        // ASSERT
        assertTrue(result.isEmpty(), "La liste doit être vide pour un client inexistant");
    }

    // ========== TEST 4: FIND BY STATUS ==========
    @Test
    @DisplayName("✅ Doit filtrer les parcours par statut")
    void testFindByStatus_Success() {
        // ACT : Chercher tous les parcours EN_COURS
        List<ParcoursEntity> activeParcours = parcoursRepository.findByStatus("EN_COURS");

        // ASSERT
        assertEquals(2, activeParcours.size(), "Doit avoir 2 parcours EN_COURS");
        assertTrue(activeParcours.stream().allMatch(p -> "EN_COURS".equals(p.getStatus())));
    }

    // ========== TEST 5: FIND BY STATUS - COMPLETED ==========
    @Test
    @DisplayName("✅ Doit filtrer les parcours complétés")
    void testFindByCompleted_Success() {
        // ACT
        List<ParcoursEntity> completed = parcoursRepository.findByStatus("COMPLETED");

        // ASSERT
        assertEquals(1, completed.size(), "Doit avoir 1 parcours COMPLETED");
        assertEquals("EPARGNE", completed.get(0).getJourneyType());
    }

    // ========== TEST 6: FIND BY JOURNEY TYPE ==========
    @Test
    @DisplayName("✅ Doit filtrer les parcours par type")
    void testFindByJourneyType_Success() {
        // ACT
        List<ParcoursEntity> creditParcours = parcoursRepository.findByJourneyType("CREDIT");

        // ASSERT
        assertEquals(2, creditParcours.size(), "Doit avoir 2 parcours CREDIT");
        assertTrue(creditParcours.stream()
            .allMatch(p -> "CREDIT".equals(p.getJourneyType())));
    }

    // ========== TEST 7: FIND BY CHANNEL ==========
    @Test
    @DisplayName("✅ Doit filtrer les parcours par canal")
    void testFindByChannel_Success() {
        // ACT
        List<ParcoursEntity> webParcours = parcoursRepository.findByChannel("WEB");

        // ASSERT
        assertEquals(2, webParcours.size(), "Doit avoir 2 parcours WEB");
        assertTrue(webParcours.stream().allMatch(p -> "WEB".equals(p.getChannel())));

        // ACT : Chercher parcours MOBILE
        List<ParcoursEntity> mobileParcours = parcoursRepository.findByChannel("MOBILE");

        // ASSERT
        assertEquals(1, mobileParcours.size(), "Doit avoir 1 parcours MOBILE");
        assertEquals("CLIENT001", mobileParcours.get(0).getClientId());
    }

    // ========== TEST 8: FIND BY CLIENT ID AND STATUS ==========
    @Test
    @DisplayName("✅ Doit filtrer par client ET statut")
    void testFindByClientIdAndStatus_Success() {
        // ACT
        List<ParcoursEntity> result = parcoursRepository
            .findByClientIdAndStatus("CLIENT001", "COMPLETED");

        // ASSERT
        assertEquals(1, result.size(), "CLIENT001 n'a qu'1 parcours COMPLETED");
        assertEquals("EPARGNE", result.get(0).getJourneyType());
    }

    // ========== TEST 9: UPDATE PARCOURS ==========
    @Test
    @DisplayName("✅ Doit mettre à jour un parcours")
    void testUpdate_Success() {
        // ARRANGE
        Optional<ParcoursEntity> found = parcoursRepository.findById(testParcours1.getId());
        assertTrue(found.isPresent());

        // ACT : Modifier le parcours
        ParcoursEntity toUpdate = found.get();
        toUpdate.setStatus("INTERRUPTED");
        toUpdate.setInterruptedAt(LocalDateTime.now());
        parcoursRepository.save(toUpdate);

        // ASSERT : Vérifier la modification
        Optional<ParcoursEntity> updated = parcoursRepository.findById(testParcours1.getId());
        assertTrue(updated.isPresent());
        assertEquals("INTERRUPTED", updated.get().getStatus());
        assertNotNull(updated.get().getInterruptedAt());
    }

    // ========== TEST 10: DELETE PARCOURS ==========
    @Test
    @DisplayName("✅ Doit supprimer un parcours")
    void testDelete_Success() {
        // ARRANGE
        Long idToDelete = testParcours1.getId();
        assertTrue(parcoursRepository.existsById(idToDelete));

        // ACT
        parcoursRepository.deleteById(idToDelete);

        // ASSERT
        assertFalse(parcoursRepository.existsById(idToDelete),
            "Le parcours doit être supprimé");
    }

    // ========== TEST 11: COUNT TOTAL ==========
    @Test
    @DisplayName("✅ Doit compter le nombre total de parcours")
    void testCount_Success() {
        // ACT
        long count = parcoursRepository.count();

        // ASSERT
        assertEquals(3, count, "Doit avoir 3 parcours en total");
    }

}
