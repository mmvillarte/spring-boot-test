package com.gft.test.app.service;

import com.gft.test.app.client.OpenDataAragonClient;
import com.gft.test.app.config.OpenDataAragonConfig;
import com.gft.test.app.exception.ResourceNotFoundException;
import com.gft.test.app.model.OpenDataAragonModel;
import com.gft.test.app.repository.OpenDataAragonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpenDataAragonService {
    private final OpenDataAragonClient openDataAragonClient;
    private final OpenDataAragonRepository openDataAragonRepository;
    private final OpenDataAragonConfig openDataAragonConfig;

    public OpenDataAragonService(OpenDataAragonClient openDataAragonClient, OpenDataAragonRepository openDataAragonRepository, OpenDataAragonConfig openDataAragonConfig) {
        this.openDataAragonClient = openDataAragonClient;
        this.openDataAragonRepository = openDataAragonRepository;
        this.openDataAragonConfig = openDataAragonConfig;
    }

    public List<OpenDataAragonModel> getAllServices() {
        List<OpenDataAragonModel> openDataAragonModels = openDataAragonClient.getAvailableServices(openDataAragonConfig.getPath() + openDataAragonConfig.getFormat());

        if(openDataAragonModels != null && !openDataAragonModels.isEmpty()) {
            upsertAll(openDataAragonModels);
        }

        return openDataAragonModels;
    }

    public List<OpenDataAragonModel> getAllServicesWebFlux() {
        List<OpenDataAragonModel> openDataAragonModels = openDataAragonClient.getAvailableServicesWebFlux(openDataAragonConfig.getPath() + openDataAragonConfig.getFormat());

        if(openDataAragonModels != null && !openDataAragonModels.isEmpty()) {
            upsertAll(openDataAragonModels);
        }

        return openDataAragonModels;
    }

    public List<OpenDataAragonModel> getAvailableOrUnavailableServices(boolean available) {
        return openDataAragonRepository.findByAvailable(available);
    }

    public List<OpenDataAragonModel> getFilteredServices(boolean available, String partialName) {
        return openDataAragonRepository.findByAvailableAndNameContainingIgnoreCase(available, partialName);
    }

    public Page<OpenDataAragonModel> getAllServicesPageable(Pageable pageable) {
        return openDataAragonRepository.findAll(pageable);
    }

    public OpenDataAragonModel findById(int id) {
        return openDataAragonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("" + id));
    }

    public void upsertAll(List<OpenDataAragonModel> openDataAragonModels) {
        openDataAragonModels.forEach(item -> {
            Optional<OpenDataAragonModel> openDataAragonModelInDB =
                    openDataAragonRepository.findById(item.getId());

            if(openDataAragonModelInDB.isEmpty()) {
                openDataAragonRepository.save(item);
            }
        });
    }
}
