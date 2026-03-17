package org.test.opendataaragon.config;

import org.test.opendataaragon.infrastructure.client.classic.OpenDataAragonClassicSpringClient;
import org.test.opendataaragon.infrastructure.client.reactive.OpenDataAragonReactiveSpringClient;
import org.test.opendataaragon.application.service.client.classic.OpenDataAragonClassicSpringService;
import org.test.opendataaragon.application.service.client.reactive.OpenDataAragonReactiveSpringService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class OpenDataAragonBeanConfig {
    private final OpenDataAragonConfig openDataAragonConfig;

    public OpenDataAragonBeanConfig(OpenDataAragonConfig openDataAragonConfig) {
        this.openDataAragonConfig = openDataAragonConfig;
    }

    @Bean
    public RestTemplate restTemplate() {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .wiretap(true);  // Activa logging de red

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean("classicClient")
    public OpenDataAragonClassicSpringClient openDataAragonClassicSpringClient() {
        return new OpenDataAragonClassicSpringClient(restTemplate());
    }

    @Bean("reactiveClient")
    public OpenDataAragonReactiveSpringClient openDataAragonReactiveSpringClient() {
        return new OpenDataAragonReactiveSpringClient(webClient());
    }

    @Bean("classicService")
    public OpenDataAragonClassicSpringService openDataAragonClassicSpringService() {
        return new OpenDataAragonClassicSpringService(openDataAragonClassicSpringClient(),
                openDataAragonConfig);
    }

    @Bean("reactiveService")
    public OpenDataAragonReactiveSpringService openDataAragonReactiveSpringService() {
        return new OpenDataAragonReactiveSpringService(openDataAragonReactiveSpringClient(),
                openDataAragonConfig);
    }
}
