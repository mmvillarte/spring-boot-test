package com.gft.test.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "open-data-aragon")
@Data
public class OpenDataAragonConfig {
    private String path;
    private String format;
}
