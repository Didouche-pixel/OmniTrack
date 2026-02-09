package com.omnitrack.controller;

import com.omnitrack.dto.ParcoursDTO;
import com.omnitrack.entity.ParcoursEntity;
import com.omnitrack.service.ParcoursService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour ParcoursController
 * 
 * POURQUOI ? Vérifier que les endpoints REST fonctionnent correctement
 * et retournent les bons statuts HTTP et données.
 * 
 * COMMENT ? En utilisant MockMvc pour simuler des requêtes HTTP
 * sans démarrer le serveur réel.
 */
@WebMvcTest(ParcoursController.class)
@DisplayName("Tests de ParcoursController - Endpoints REST")
class ParcoursControllerTest {

    // ========== SETUP ==========
    /**
     * MockMvc : Permet de faire des requêtes HTTP sans serveur réel
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * MockBean : Mock du Service injecté dans le Controller
     */
    @MockBean
    private ParcoursService parcoursService;

    private ParcoursEntity testParcours;
    private ParcoursEntity testParcours2;
    private ParcoursDTO testParcoursDTO;

    @BeforeEach
    void setUp() {
        // Créer données de test
        testParcours = new ParcoursEntity("CLIENT001", "CREDIT", "EN_COURS", "WEB");
        testParcours.setId(1L);
        testParcours.setCreatedAt(LocalDateTime.of(2026, 2, 9, 10, 0));

        testParcours2 = new ParcoursEntity("CLIENT002", "EPARGNE", "COMPLETED", "MOBILE");
        testParcours2.setId(2L);
        testParcours2.setCreatedAt(LocalDateTime.of(2026, 1, 15, 14, 30));
        testParcours2.setCompletedAt(LocalDateTime.of(2026, 2, 8, 16, 45));

        // Créer DTO correspondant
        testParcoursDTO = new ParcoursDTO(
            1L, "CREDIT", "EN_COURS", "WEB",
            LocalDateTime.of(2026, 2, 9, 10, 0),
            null, null, null
        );
    }

    // ========== TEST 1: GET ALL PARCOURS ==========
    @Test
    @DisplayName("✅ GET /api/parcours - Retourner tous les parcours")
    void testGetAllParcours_Success() throws Exception {
        // ARRANGE
        List<ParcoursEntity> allParcours = Arrays.asList(testParcours, testParcours2);
        when(parcoursService.getAllParcours()).thenReturn(allParcours);

        // ACT & ASSERT : Faire la requête et vérifier
        mockMvc.perform(
                get("/api/parcours")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())  // ✅ Status 200
            .andExpect(jsonPath("$", hasSize(2)))  // ✅ 2 éléments
            .andExpect(jsonPath("$[0].id", is(1)))  // ✅ Premier parcours ID = 1
            .andExpect(jsonPath("$[0].journeyType", is("CREDIT")))
            .andExpect(jsonPath("$[1].journeyType", is("EPARGNE")))
            .andExpect(jsonPath("$[1].status", is("COMPLETED")));

        // VERIFY
        verify(parcoursService, times(1)).getAllParcours();
    }

    // ========== TEST 2: GET BY ID ==========
    @Test
    @DisplayName("✅ GET /api/parcours/{id} - Retourner un parcours par ID")
    void testGetParcoursById_Success() throws Exception {
        // ARRANGE
        when(parcoursService.getParcoursById(1L)).thenReturn(testParcours);

        // ACT & ASSERT
        mockMvc.perform(
                get("/api/parcours/1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk()) 
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.journeyType", is("CREDIT")))
            .andExpect(jsonPath("$.status", is("EN_COURS")))
            .andExpect(jsonPath("$.channel", is("WEB")));

        verify(parcoursService, times(1)).getParcoursById(1L);
    }

    // ========== TEST 3: GET BY ID - NOT FOUND ==========
    @Test
    @DisplayName("❌ GET /api/parcours/{id} - Erreur si parcours introuvable")
    void testGetParcoursById_NotFound() throws Exception {
        // ARRANGE
        when(parcoursService.getParcoursById(999L))
            .thenThrow(new RuntimeException("Parcours introuvable : 999"));

        // ACT & ASSERT
        mockMvc.perform(
                get("/api/parcours/999")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isInternalServerError());  // ❌ Status 500

        verify(parcoursService, times(1)).getParcoursById(999L);
    }

    // ========== TEST 4: GET BY CLIENT ID ==========
    @Test
    @DisplayName("✅ GET /api/parcours/client/{clientId} - Filtrer par client")
    void testGetParcoursByClient_Success() throws Exception {
        // ARRANGE
        List<ParcoursEntity> clientParcours = Arrays.asList(testParcours);
        when(parcoursService.getParcoursByClientId("CLIENT001"))
            .thenReturn(clientParcours);

        // ACT & ASSERT
        mockMvc.perform(
                get("/api/parcours/client/CLIENT001")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())  // ✅ Status 200
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].status", is("EN_COURS")));

        verify(parcoursService, times(1)).getParcoursByClientId("CLIENT001");
    }

    // ========== TEST 5: GET BY STATUS ==========
    @Test
    @DisplayName("✅ GET /api/parcours/status/{status} - Filtrer par statut")
    void testGetParcoursByStatus_Success() throws Exception {
        // ARRANGE
        List<ParcoursEntity> completedParcours = Arrays.asList(testParcours2);
        when(parcoursService.getParcoursByStatus("COMPLETED"))
            .thenReturn(completedParcours);

        // ACT & ASSERT
        mockMvc.perform(
                get("/api/parcours/status/COMPLETED")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())  // ✅ Status 200
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].status", is("COMPLETED")))
            .andExpect(jsonPath("$[0].journeyType", is("EPARGNE")));

        verify(parcoursService, times(1)).getParcoursByStatus("COMPLETED");
    }

    // ========== TEST 6: CREATE PARCOURS ==========
    @Test
    @DisplayName("✅ POST /api/parcours - Créer un nouveau parcours")
    void testCreateParcours_Success() throws Exception {
        // ARRANGE
        when(parcoursService.createParcours(testParcours)).thenReturn(testParcours);

        // ACT & ASSERT
        mockMvc.perform(
                post("/api/parcours")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"clientId\": \"CLIENT001\", \"journeyType\": \"CREDIT\", " +
                             "\"status\": \"EN_COURS\", \"cannel\": \"WEB\"}")
            )
            .andExpect(status().isOk())  // ✅ Status 200
            .andExpect(jsonPath("$.journeyType", is("CREDIT")))
            .andExpect(jsonPath("$.status", is("EN_COURS")));

        verify(parcoursService, times(1)).createParcours(any(ParcoursEntity.class));
    }

    // ========== TEST 7: INTERRUPT PARCOURS ==========
    @Test
    @DisplayName("✅ PUT /api/parcours/{id}/interrupt - Interrompre un parcours")
    void testInterruptParcours_Success() throws Exception {
        // ARRANGE
        testParcours.setStatus("INTERRUPTED");
        testParcours.setInterruptedAt(LocalDateTime.now());
        when(parcoursService.interruptParcours(1L)).thenReturn(testParcours);

        // ACT & ASSERT
        mockMvc.perform(
                put("/api/parcours/1/interrupt")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())  // ✅ Status 200
            .andExpect(jsonPath("$.status", is("INTERRUPTED")))
            .andExpect(jsonPath("$.interruptedAt", notNullValue()));

        verify(parcoursService, times(1)).interruptParcours(1L);
    }

    // ========== TEST 8: COMPLETE PARCOURS ==========
    @Test
    @DisplayName("✅ PUT /api/parcours/{id}/complete - Compléter un parcours")
    void testCompleteParcours_Success() throws Exception {
        // ARRANGE
        testParcours.setStatus("COMPLETED");
        testParcours.setCompletedAt(LocalDateTime.now());
        when(parcoursService.completeParcours(1L)).thenReturn(testParcours);

        // ACT & ASSERT
        mockMvc.perform(
                put("/api/parcours/1/complete")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())  // ✅ Status 200
            .andExpect(jsonPath("$.status", is("COMPLETED")))
            .andExpect(jsonPath("$.completedAt", notNullValue()));

        verify(parcoursService, times(1)).completeParcours(1L);
    }

}
