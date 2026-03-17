package org.test.opendataaragon.infrastructure.client;

import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;

import java.util.List;

public interface OpenDataAragonClient {
    List<OpenDataAragonModel> getAvailableServices(String path);
}
