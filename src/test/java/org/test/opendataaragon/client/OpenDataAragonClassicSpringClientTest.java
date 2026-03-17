package org.test.opendataaragon.client;

import org.test.opendataaragon.infrastructure.client.OpenDataAragonClient;
import org.test.opendataaragon.infrastructure.client.classic.OpenDataAragonClassicSpringClient;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OpenDataAragonClassicSpringClientTest {

    private RestTemplate restTemplate;
    private OpenDataAragonClient client;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        client = new OpenDataAragonClassicSpringClient(restTemplate);
    }

    @Test
    void returnsListOfServicesWhenRestTemplateReturnsArray() {
        OpenDataAragonModel[] models = { new OpenDataAragonModel() };
        when(restTemplate.getForEntity(anyString(), eq(OpenDataAragonModel[].class)))
                .thenReturn(ResponseEntity.ok(models));

        List<OpenDataAragonModel> result = client.getAvailableServices("http://example.com");

        assertEquals(1, result.size());
    }

    @Test
    void returnsEmptyListWhenRestTemplateReturnsNullBody() {
        when(restTemplate.getForEntity(anyString(), eq(OpenDataAragonModel[].class)))
                .thenReturn(ResponseEntity.ok(null));

        List<OpenDataAragonModel> result = client.getAvailableServices("http://example.com");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void propagatesExceptionWhenRestTemplateFails() {
        when(restTemplate.getForEntity(anyString(), eq(OpenDataAragonModel[].class)))
                .thenThrow(new RuntimeException("rest error"));

        assertThrows(RuntimeException.class, () -> client.getAvailableServices("http://example.com"));
    }
}