package com.gft.test.app.client;

import com.gft.test.app.model.OpenDataAragonModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
public class OpenDataAragonClient {
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public OpenDataAragonClient(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    public List<OpenDataAragonModel> getAvailableServices(String path) {
        return Optional.ofNullable(
                restTemplate.getForEntity(path, OpenDataAragonModel[].class).getBody()
        ).map(List::of).orElseGet(List::of);
    }

    public List<OpenDataAragonModel> getAvailableServicesWebFlux(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(OpenDataAragonModel.class)
                .collectList()
                .block();
    }
}
