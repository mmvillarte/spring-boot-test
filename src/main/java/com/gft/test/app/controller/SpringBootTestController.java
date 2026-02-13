package com.gft.test.app.controller;

import com.gft.test.app.model.OpenDataAragonModel;
import com.gft.test.app.service.OpenDataAragonService;
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
@RequestMapping("/test")
public class SpringBootTestController {
    private final OpenDataAragonService openDataAragonService;

    public final static String HELLO_WORLD = "Hello World!";

    public SpringBootTestController(OpenDataAragonService openDataAragonService) {
        this.openDataAragonService = openDataAragonService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return HELLO_WORLD;
    }

    @GetMapping("/aragon/services/all")
    public List<OpenDataAragonModel> getAllServices() {
        return openDataAragonService.getAllServices();
    }

    @GetMapping("/aragon/services/webflux/all")
    public List<OpenDataAragonModel> getAllServicesWebFlux() {
        return openDataAragonService.getAllServicesWebFlux();
    }

    @GetMapping("/aragon/services/available")
    public List<OpenDataAragonModel> getAvailableServices(@RequestParam boolean available) {
        return openDataAragonService.getAvailableOrUnavailableServices(available);
    }

    @GetMapping("/aragon/services/filterBy")
    public List<OpenDataAragonModel> getFilteredServices(@RequestParam boolean available, @RequestParam String partialName) {
        return openDataAragonService.getFilteredServices(available, partialName);
    }

    @GetMapping("/aragon/services/all/pageable")
    public Page<OpenDataAragonModel> getAllServicesPageable(@ParameterObject @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        return openDataAragonService.getAllServicesPageable(pageable);
    }

    @GetMapping("/aragon/services/findById")
    public OpenDataAragonModel findById(@RequestParam int id) {
        return openDataAragonService.findById(id);
    }
}
