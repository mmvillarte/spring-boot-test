package com.gft.test.app.service;

import com.gft.test.app.client.OpenDataAragonClient;
import com.gft.test.app.config.OpenDataAragonConfig;
import com.gft.test.app.model.OpenDataAragonModel;
import com.gft.test.app.repository.OpenDataAragonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

class OpenDataAragonServiceTest {
    @Mock
    OpenDataAragonClient openDataAragonClient;

    @Mock
    OpenDataAragonRepository openDataAragonRepository;

    @Mock
    OpenDataAragonConfig openDataAragonConfig;

    @InjectMocks
    OpenDataAragonService openDataAragonService;

    public static final String BASE_PATH_TEST = "http://test/path?form=";
    public static final String FORMAT_TEST = "json";

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllServices() {
        when(openDataAragonConfig.getPath()).thenReturn(BASE_PATH_TEST);
        when(openDataAragonConfig.getFormat()).thenReturn(FORMAT_TEST);

        List<OpenDataAragonModel> testData = getTestData();

        when(openDataAragonClient.getAvailableServices(openDataAragonConfig.getPath()
                + openDataAragonConfig.getFormat())). thenReturn(testData);

        List<OpenDataAragonModel> resultTest = openDataAragonService.getAllServices();

        Assertions.assertNotNull(resultTest);
        Assertions.assertEquals(2, resultTest.size());
        Assertions.assertEquals(testData, resultTest);
    }

    @Test
    void getAllServicesWebFlux() {
        when(openDataAragonConfig.getPath()).thenReturn(BASE_PATH_TEST);
        when(openDataAragonConfig.getFormat()).thenReturn(FORMAT_TEST);

        List<OpenDataAragonModel> testData = getTestData();

        when(openDataAragonClient.getAvailableServicesWebFlux(openDataAragonConfig.getPath()
                + openDataAragonConfig.getFormat())). thenReturn(testData);

        List<OpenDataAragonModel> resultTest = openDataAragonService.getAllServicesWebFlux();

        Assertions.assertNotNull(resultTest);
        Assertions.assertEquals(2, resultTest.size());
        Assertions.assertEquals(testData, resultTest);
    }

    @Test
    void getAvailableOrUnavailableServices() {
        boolean availableTest = true;
        List<OpenDataAragonModel> testAvailableData = getAvailableTestData(availableTest);

        when(openDataAragonRepository.findByAvailable(availableTest)).thenReturn(testAvailableData);

        List<OpenDataAragonModel> resultTest = openDataAragonService.getAvailableOrUnavailableServices(availableTest);

        Assertions.assertNotNull(resultTest);
        Assertions.assertEquals(1, resultTest.size());
        Assertions.assertEquals(testAvailableData, resultTest);
    }

    public List<OpenDataAragonModel> getTestData() {
        return Arrays.asList(
                new OpenDataAragonModel(1, "Data 1", true),
                new OpenDataAragonModel(2, "Data 2", false)
        );
    }

    public List<OpenDataAragonModel> getAvailableTestData(boolean availableTest) {
        return List.of(new OpenDataAragonModel(1, "Data 1", availableTest));
    }
}