package com.omnitrack.client;

import com.omnitrack.dto.ParcoursDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Component
public class BackendApiClient {

    private final WebClient webClient;

    public BackendApiClient(@Value("${backend.api.url}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    /* GET - Récupérer tous les parcours */
    public List<ParcoursDTO> getAllParcours() {
        return webClient.get()
                .uri("/api/parcours")
                .retrieve()
                .bodyToMono(ParcoursDTO[].class)
                .map(Arrays::asList)
                .block();
    }
    public ParcoursDTO getParcoursById(Long id){
        return webClient.get()
                .uri("/api/parcours/{id}", id)
                .retrieve()
                .bodyToMono(ParcoursDTO.class)
                .block();
    }
    public List<ParcoursDTO> getParcoursByClient(String clientId){

        return webClient.get()
                .uri("/api/parcours/client/{clientId}", clientId)
                .retrieve()
                .bodyToMono(ParcoursDTO[].class)
                .map(Arrays::asList)
                .block();
    }
    public List<ParcoursDTO> getParcoursByStatus(String status){
        return webClient.get()
                .uri("/api/parcours/status/{status}", status)
                .retrieve()
                .bodyToMono(ParcoursDTO[].class)
                .map(Arrays::asList)
                .block();
    }
    public List<ParcoursDTO> getParcoursByJourneyType(String journeyType){
        return webClient.get()
                .uri("/api/parcours/type/{journeyType}", journeyType)
                .retrieve()
                .bodyToMono(ParcoursDTO[].class)
                .map(Arrays::asList)
                .block();
    }
    public List<ParcoursDTO> getParcoursByChannel(String channel){
        return webClient.get()
                .uri("/api/parcours/channel/{channel}", channel)
                .retrieve()
                .bodyToMono(ParcoursDTO[].class)
                .map(Arrays::asList)
                .block();
    }
    public ParcoursDTO createParcours(ParcoursDTO parcoursDTO){
        return webClient.post()
                .uri("/api/parcours")
                .bodyValue(parcoursDTO)
                .retrieve()
                .bodyToMono(ParcoursDTO.class)
                .block();
    }
    public ParcoursDTO interruptParcours(Long id){
        return webClient.put()
                .uri("/api/parcours/{id}/interrupt", id)
                .retrieve()
                .bodyToMono(ParcoursDTO.class)
                .block();
    }

    public ParcoursDTO resumeParcours(Long id){
        return webClient.put()
                .uri("/api/parcours/{id}/resume", id)
                .retrieve()
                .bodyToMono(ParcoursDTO.class)
                .block();
    }
    public ParcoursDTO completeParcours(Long id){
        return webClient.put()
                .uri("/api/parcours/{id}/complete", id)
                .retrieve()
                .bodyToMono(ParcoursDTO.class)
                .block();
    }
}
