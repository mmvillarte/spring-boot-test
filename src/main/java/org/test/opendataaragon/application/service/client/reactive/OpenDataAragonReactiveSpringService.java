package org.test.opendataaragon.application.service.client.reactive;

import org.test.opendataaragon.infrastructure.client.OpenDataAragonClient;
import org.test.opendataaragon.config.OpenDataAragonConfig;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.test.opendataaragon.application.service.client.OpenDataAragonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenDataAragonReactiveSpringService implements OpenDataAragonService {
    private final OpenDataAragonClient openDataAragonClient;
    private final OpenDataAragonConfig openDataAragonConfig;

    public OpenDataAragonReactiveSpringService(@Qualifier("reactiveClient") OpenDataAragonClient openDataAragonClient, OpenDataAragonConfig openDataAragonConfig) {
        this.openDataAragonClient = openDataAragonClient;
        this.openDataAragonConfig = openDataAragonConfig;
    }

    @Override
    public List<OpenDataAragonModel> loadAllServices() {
        return openDataAragonClient.getAvailableServices(openDataAragonConfig.getPath()
                + openDataAragonConfig.getFormat());
    }
}
