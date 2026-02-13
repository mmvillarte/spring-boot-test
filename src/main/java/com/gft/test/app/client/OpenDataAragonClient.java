package com.gft.test.app.client;

import com.gft.test.app.model.OpenDataAragonModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OpenDataAragonClient {
    private final RestTemplate restTemplate;

    public OpenDataAragonClient(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    public List<OpenDataAragonModel> getAvailableServices(String path) {
        ResponseEntity<OpenDataAragonModel[]> response =
                restTemplate.getForEntity(path, OpenDataAragonModel[].class);

        return (response.getBody() != null) ? Arrays.asList(response.getBody()): new ArrayList<>();
    }

    private final WebClient webClient;

    public List<OpenDataAragonModel> getAvailableServicesWebFlux(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(OpenDataAragonModel.class)
                .collectList()
                .block();
    }
}
