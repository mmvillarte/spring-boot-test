package org.test.opendataaragon.api.controller.query;

import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.test.opendataaragon.application.service.query.OpenDataAragonQueryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/query")
public class OpenDataAragonQueryController {
    private final OpenDataAragonQueryService openDataAragonQueryService;

    public OpenDataAragonQueryController(OpenDataAragonQueryService openDataAragonQueryService) {
        this.openDataAragonQueryService = openDataAragonQueryService;
    }

    @GetMapping("/aragon/services/available")
    public List<OpenDataAragonModel> getAvailableServices(@RequestParam boolean available) {
        return openDataAragonQueryService.getAvailableOrUnavailableServices(available);
    }

    @GetMapping("/aragon/services/filterBy")
    public List<OpenDataAragonModel> getFilteredServices(@RequestParam boolean available, @RequestParam String partialName) {
        return openDataAragonQueryService.getFilteredServices(available, partialName);
    }

    @GetMapping("/aragon/services/all/pageable")
    public Page<OpenDataAragonModel> getAllServicesPageable(@ParameterObject @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        return openDataAragonQueryService.getAllServicesPageable(pageable);
    }

    @GetMapping("/aragon/services/findById")
    public OpenDataAragonModel findById(@RequestParam int id) {
        return openDataAragonQueryService.findById(id);
    }
}
