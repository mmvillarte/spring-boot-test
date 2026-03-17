package org.test.opendataaragon.infrastructure.client.classic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.test.opendataaragon.infrastructure.persistence.model.OpenDataAragonModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class OpenDataAragonClassicSpringClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenDataAragonClassicSpringClient openDataAragonClassicSpringClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableServices() {
        OpenDataAragonModel[] models = { new OpenDataAragonModel() };
        when(restTemplate.getForEntity(anyString(), eq(OpenDataAragonModel[].class)))
                .thenReturn(ResponseEntity.ok(models));

        List<OpenDataAragonModel> result = openDataAragonClassicSpringClient.getAvailableServices("http://example.com");

        assertEquals(1, result.size());
    }
}