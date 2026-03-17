package org.test.opendataaragon.infrastructure.persistence.repository;

import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpenDataAragonRepository extends JpaRepository<OpenDataAragonModel, Long> {
    List<OpenDataAragonModel> findByAvailable(boolean available);
    Optional<OpenDataAragonModel> findById(int id);
    List<OpenDataAragonModel> findByAvailableAndNameContainingIgnoreCase(boolean available, String partialName);
}
