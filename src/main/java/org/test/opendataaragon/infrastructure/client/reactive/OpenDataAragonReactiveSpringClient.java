package org.test.opendataaragon.infrastructure.client.reactive;

import org.test.opendataaragon.infrastructure.client.OpenDataAragonClient;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OpenDataAragonReactiveSpringClient implements OpenDataAragonClient {
    private final WebClient webClient;

    public OpenDataAragonReactiveSpringClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<OpenDataAragonModel> getAvailableServices(String path) {
        return webClient.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(OpenDataAragonModel.class)
                .collectList()
                .block();
    }
}
