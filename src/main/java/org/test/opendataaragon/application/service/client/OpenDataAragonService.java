package org.test.opendataaragon.application.service.client;

import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;

import java.util.List;

public interface OpenDataAragonService {
    List<OpenDataAragonModel> getAllServices();
}
