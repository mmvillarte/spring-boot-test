package com.gft.test.app.client;

import com.gft.test.app.model.OpenDataAragonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OpenDataAragonClientTest {

    private RestTemplate restTemplate;
    private WebClient webClient;
    private OpenDataAragonClient client;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        webClient = mock(WebClient.class, RETURNS_DEEP_STUBS);
        client = new OpenDataAragonClient(restTemplate, webClient);
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

    @Test
    void returnsListOfServicesWhenWebClientReturnsFlux() {
        OpenDataAragonModel model = new OpenDataAragonModel();
        when(webClient.get().uri(anyString()).retrieve().bodyToFlux(OpenDataAragonModel.class))
                .thenReturn(Flux.just(model));

        List<OpenDataAragonModel> result = client.getAvailableServicesWebFlux("http://example.com");

        assertEquals(1, result.size());
    }

    @Test
    void returnsEmptyListWhenWebClientReturnsEmptyFlux() {
        when(webClient.get().uri(Mockito.anyString()).retrieve().bodyToFlux(OpenDataAragonModel.class))
                .thenReturn(Flux.empty());

        List<OpenDataAragonModel> result = client.getAvailableServicesWebFlux("http://example.com");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void propagatesExceptionWhenWebClientFails() {
        when(webClient.get().uri(anyString()).retrieve().bodyToFlux(OpenDataAragonModel.class))
                .thenThrow(new RuntimeException("webclient error"));

        assertThrows(RuntimeException.class, () -> client.getAvailableServicesWebFlux("http://example.com"));
    }
}