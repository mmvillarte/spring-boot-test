package org.test.opendataaragon.api.controller.reactive;

import lombok.extern.slf4j.Slf4j;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.test.opendataaragon.application.service.client.OpenDataAragonService;
import org.test.opendataaragon.application.service.query.OpenDataAragonQueryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/reactive")
@Slf4j
public class OpenDataAragonReactiveSpringController {
    private final OpenDataAragonService openDataAragonService;
    private final OpenDataAragonQueryService openDataAragonQueryService;

    public OpenDataAragonReactiveSpringController(@Qualifier("reactiveService") OpenDataAragonService openDataAragonService,
                                                  OpenDataAragonQueryService openDataAragonQueryService) {
        this.openDataAragonService = openDataAragonService;
        this.openDataAragonQueryService = openDataAragonQueryService;
    }

    @GetMapping("/aragon/services/load/all")
    public List<OpenDataAragonModel> loadAllServices() {
        log.info("Loading all services (reactive)");
        List<OpenDataAragonModel> openDataAragonModels = openDataAragonService.loadAllServices();

        if(openDataAragonModels != null && !openDataAragonModels.isEmpty()) {
            log.info("Services retrieved: {}. Upserting to database (reactive)", openDataAragonModels.size());
            openDataAragonQueryService.upsertAll(openDataAragonModels);
        }

        return openDataAragonModels;
    }
}
