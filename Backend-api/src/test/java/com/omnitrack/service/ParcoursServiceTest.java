package com.omnitrack.service;

import com.omnitrack.entity.ParcoursEntity;
import com.omnitrack.repository.ParcoursRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour ParcoursService
 * 
 * POURQUOI ? Vérifier que la logique métier fonctionne correctement
 * sans accéder à la vraie base de données.
 * 
 * COMMENT ? En utilisant des mocks du Repository
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de ParcoursService - Logique métier")
class ParcoursServiceTest {

    // ========== SETUP ==========
    /**
     * @Mock : Simule le Repository (pas de vraie BDD)
     * @InjectMocks : Injecte automatiquement les mocks dans le Service
     */
    @Mock
    private ParcoursRepository parcoursRepository;

    @InjectMocks
    private ParcoursService parcoursService;

    /**
     * Données de test réutilisables
     */
    private ParcoursEntity testParcours;
    private ParcoursEntity testParcours2;

    @BeforeEach
    void setUp() {
        // Créer des parcours de test
        testParcours = new ParcoursEntity("CLIENT001", "CREDIT", "EN_COURS", "WEB");
        testParcours.setId(1L);
        testParcours.setCreatedAt(LocalDateTime.now());

        testParcours2 = new ParcoursEntity("CLIENT002", "EPARGNE", "EN_COURS", "MOBILE");
        testParcours2.setId(2L);
        testParcours2.setCreatedAt(LocalDateTime.now());
    }

    // ========== TEST 1: GET ALL PARCOURS ==========
    @Test
    @DisplayName("✅ Doit retourner tous les parcours")
    void testGetAllParcours_Success() {
        // 1. ARRANGE : Configurer le mock
        List<ParcoursEntity> mockList = Arrays.asList(testParcours, testParcours2);
        when(parcoursRepository.findAll()).thenReturn(mockList);

        // 2. ACT : Appeler la méthode
        List<ParcoursEntity> result = parcoursService.getAllParcours();

        // 3. ASSERT : Vérifier le résultat
        assertNotNull(result, "La liste ne doit pas être null");
        assertEquals(2, result.size(), "La liste doit contenir 2 parcours");
        assertEquals("CLIENT001", result.get(0).getClientId());
        assertEquals("CLIENT002", result.get(1).getClientId());

        // 4. VERIFY : Vérifier que la méthode a été appelée
        verify(parcoursRepository, times(1)).findAll();
    }

    // ========== TEST 2: GET BY ID ==========
    @Test
    @DisplayName("✅ Doit retourner un parcours par ID")
    void testGetParcoursById_Success() {
        // ARRANGE
        when(parcoursRepository.findById(1L)).thenReturn(Optional.of(testParcours));

        // ACT
        ParcoursEntity result = parcoursService.getParcoursById(1L);

        // ASSERT
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CLIENT001", result.getClientId());
        verify(parcoursRepository, times(1)).findById(1L);
    }

    // ========== TEST 3: GET BY ID - PARCOURS NOT FOUND ==========
    @Test
    @DisplayName("❌ Doit lever une exception si parcours introuvable")
    void testGetParcoursById_NotFound() {
        // ARRANGE
        when(parcoursRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT & ASSERT : Vérifier que l'exception est levée
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> parcoursService.getParcoursById(999L),
            "Devrait lever RuntimeException"
        );

        // Vérifier le message d'erreur
        assertTrue(exception.getMessage().contains("Parcours introuvable"));
        verify(parcoursRepository, times(1)).findById(999L);
    }

    // ========== TEST 4: INTERRUPT PARCOURS ==========
    @Test
    @DisplayName("✅ Doit interrompre un parcours correctement")
    void testInterruptParcours_Success() {
        // ARRANGE
        when(parcoursRepository.findById(1L)).thenReturn(Optional.of(testParcours));
        when(parcoursRepository.save(testParcours)).thenReturn(testParcours);

        // ACT
        ParcoursEntity result = parcoursService.interruptParcours(1L);

        // ASSERT
        assertEquals("INTERRUPTED", result.getStatus());
        assertNotNull(result.getInterruptedAt(), "interruptedAt doit être défini");

        // Vérifier les interactions
        verify(parcoursRepository, times(1)).findById(1L);
        verify(parcoursRepository, times(1)).save(testParcours);
    }

    // ========== TEST 5: COMPLETE PARCOURS ==========
    @Test
    @DisplayName("✅ Doit compléter un parcours correctement")
    void testCompleteParcours_Success() {
        // ARRANGE
        when(parcoursRepository.findById(1L)).thenReturn(Optional.of(testParcours));
        when(parcoursRepository.save(testParcours)).thenReturn(testParcours);

        // ACT
        ParcoursEntity result = parcoursService.completeParcours(1L);

        // ASSERT
        assertEquals("COMPLETED", result.getStatus());
        assertNotNull(result.getCompletedAt(), "completedAt doit être défini");
        verify(parcoursRepository, times(1)).save(testParcours);
    }

    // ========== TEST 6: GET BY CLIENT ID ==========
    @Test
    @DisplayName("✅ Doit retourner tous les parcours d'un client")
    void testGetParcoursByClientId_Success() {
        // ARRANGE
        List<ParcoursEntity> clientParcours = Arrays.asList(testParcours);
        when(parcoursRepository.findByClientId("CLIENT001"))
            .thenReturn(clientParcours);

        // ACT
        List<ParcoursEntity> result = parcoursService.getParcoursByClientId("CLIENT001");

        // ASSERT
        assertEquals(1, result.size());
        assertEquals("CLIENT001", result.get(0).getClientId());
        verify(parcoursRepository, times(1)).findByClientId("CLIENT001");
    }

    // ========== TEST 7: GET BY STATUS ==========
    @Test
    @DisplayName("✅ Doit filtrer les parcours par statut")
    void testGetParcoursByStatus_Success() {
        // ARRANGE
        List<ParcoursEntity> activeParcours = Arrays.asList(testParcours, testParcours2);
        when(parcoursRepository.findByStatus("EN_COURS"))
            .thenReturn(activeParcours);

        // ACT
        List<ParcoursEntity> result = parcoursService.getParcoursByStatus("EN_COURS");

        // ASSERT
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> "EN_COURS".equals(p.getStatus())));
        verify(parcoursRepository, times(1)).findByStatus("EN_COURS");
    }

}
