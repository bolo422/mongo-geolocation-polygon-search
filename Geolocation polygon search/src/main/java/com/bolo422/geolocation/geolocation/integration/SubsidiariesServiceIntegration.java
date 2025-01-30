package com.bolo422.geolocation.geolocation.integration;

import com.bolo422.geolocation.geolocation.integration.config.UrlProperties;
import com.bolo422.geolocation.geolocation.model.response.SubsidiariesIntegrationResponseWrapper;
import com.bolo422.geolocation.geolocation.model.Subsidiary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@EnableConfigurationProperties(UrlProperties.class)
@RequiredArgsConstructor
@Slf4j
public class SubsidiariesServiceIntegration {
    private final RestTemplate internalServicesRestTemplate;
    private final UrlProperties urlProperties;

    public List<Subsidiary> findSubsidiaries(String state) {
        final var url = urlProperties.subsidiaries() + "?pageNumber=0&pageSize=1000&sort=ASC&uf=" + state;
        log.info("Requesting subsidiaries from {}", url);
        return Optional.ofNullable(internalServicesRestTemplate.getForObject(url, SubsidiariesIntegrationResponseWrapper.class))
                .map(SubsidiariesIntegrationResponseWrapper::content)
                .orElse(Collections.emptyList());
    }
}
