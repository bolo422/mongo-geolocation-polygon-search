package com.bolo422.geolocation.geolocation.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(UrlProperties.class)
@RequiredArgsConstructor
public class InternalServicesRestTemplateConfig {

    private final UrlProperties urlProperties;
    @Bean
    public RestTemplate internalServicesRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.rootUri(urlProperties.base()).build();
    }
}
