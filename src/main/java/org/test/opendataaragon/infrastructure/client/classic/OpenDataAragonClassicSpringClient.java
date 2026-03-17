package org.test.opendataaragon.infrastructure.client.classic;

import org.test.opendataaragon.infrastructure.client.OpenDataAragonClient;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class OpenDataAragonClassicSpringClient implements OpenDataAragonClient {
    private final RestTemplate restTemplate;

    public OpenDataAragonClassicSpringClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<OpenDataAragonModel> getAvailableServices(String path) {
        return Optional.ofNullable(
                restTemplate.getForEntity(path, OpenDataAragonModel[].class).getBody()
        ).map(List::of).orElseGet(List::of);
    }
}
