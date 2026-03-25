package org.test.opendataaragon.application.service.query;

import lombok.extern.slf4j.Slf4j;
import org.test.opendataaragon.api.exception.ResourceNotFoundException;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.test.opendataaragon.infrastructure.persistence.repository.OpenDataAragonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OpenDataAragonQueryService {
    private final OpenDataAragonRepository openDataAragonRepository;

    public OpenDataAragonQueryService(OpenDataAragonRepository openDataAragonRepository) {
        this.openDataAragonRepository = openDataAragonRepository;
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
        Objects.requireNonNull(openDataAragonModels, "OpenDataAragonModels cannot be null");

        AtomicInteger upsertedCount = new AtomicInteger();
        openDataAragonModels.forEach(item -> {
            Optional<OpenDataAragonModel> openDataAragonModelInDB =
                    openDataAragonRepository.findById(item.getId());

            if(openDataAragonModelInDB.isEmpty()) {
                openDataAragonRepository.save(item);
                upsertedCount.getAndIncrement();
            }
        });

        log.info("Services Upserted: {}", upsertedCount);
    }

}
