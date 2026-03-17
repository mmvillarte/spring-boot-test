package org.test.opendataaragon.application.service.query;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.test.opendataaragon.api.exception.ResourceNotFoundException;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.test.opendataaragon.infrastructure.persistence.repository.OpenDataAragonRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenDataAragonQueryServiceTest {
    @Mock
    private OpenDataAragonRepository repository;

    @InjectMocks
    private OpenDataAragonQueryService service;

    @Test
    void testGetAvailableOrUnavailableServices() {
        OpenDataAragonModel model = new OpenDataAragonModel();
        model.setId(1);
        model.setAvailable(true);

        when(repository.findByAvailable(true)).thenReturn(List.of(model));

        List<OpenDataAragonModel> result = service.getAvailableOrUnavailableServices(true);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    void testGetFilteredServices() {
        OpenDataAragonModel model = new OpenDataAragonModel();
        model.setId(2);
        model.setName("Test Service");
        model.setAvailable(true);

        when(repository.findByAvailableAndNameContainingIgnoreCase(true, "Test"))
                .thenReturn(List.of(model));

        List<OpenDataAragonModel> result = service.getFilteredServices(true, "Test");

        assertEquals(1, result.size());
        assertEquals("Test Service", result.get(0).getName());
    }

    @Test
    void testGetAllServicesPageable() {
        OpenDataAragonModel model1 = new OpenDataAragonModel();
        OpenDataAragonModel model2 = new OpenDataAragonModel();
        Page<OpenDataAragonModel> page = new PageImpl<>(List.of(model1, model2));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<OpenDataAragonModel> result = service.getAllServicesPageable(Pageable.unpaged());

        assertEquals(2, result.getContent().size());
    }

    @Test
    void testFindByIdFound() {
        OpenDataAragonModel model = new OpenDataAragonModel();
        model.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(model));

        OpenDataAragonModel result = service.findById(1);
        assertEquals(1, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(999));
    }

    @Test
    void testUpsertAllOnlyInsertsNew() {
        OpenDataAragonModel existing = new OpenDataAragonModel();
        existing.setId(1);

        OpenDataAragonModel newModel = new OpenDataAragonModel();
        newModel.setId(2);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.findById(2)).thenReturn(Optional.empty());

        service.upsertAll(List.of(existing, newModel));

        verify(repository, never()).save(existing);
        verify(repository, times(1)).save(newModel);
    }
}