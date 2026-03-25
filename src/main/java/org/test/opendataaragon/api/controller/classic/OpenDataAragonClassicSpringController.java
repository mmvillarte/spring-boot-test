package org.test.opendataaragon.api.controller.classic;

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
@RequestMapping("/test/classic")
@Slf4j
public class OpenDataAragonClassicSpringController {
    private final OpenDataAragonService openDataAragonService;
    private final OpenDataAragonQueryService openDataAragonQueryService;

    public OpenDataAragonClassicSpringController(@Qualifier("classicService") OpenDataAragonService openDataAragonService,
                                                 OpenDataAragonQueryService openDataAragonQueryService) {
        this.openDataAragonService = openDataAragonService;
        this.openDataAragonQueryService = openDataAragonQueryService;
    }

    @GetMapping("/aragon/services/load/all")
    public List<OpenDataAragonModel> loadAllServices() {
        log.info("Loading all services (classic)");
        List<OpenDataAragonModel> openDataAragonModels = openDataAragonService.loadAllServices();

        if(openDataAragonModels != null && !openDataAragonModels.isEmpty()) {
            log.info("Services retrieved: {}. Upserting to database (classic)", openDataAragonModels.size());
            openDataAragonQueryService.upsertAll(openDataAragonModels);
        }

        return openDataAragonModels;
    }
}
