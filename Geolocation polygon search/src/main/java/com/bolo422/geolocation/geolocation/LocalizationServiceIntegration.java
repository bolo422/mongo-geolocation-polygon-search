package com.bolo422.geolocation.geolocation;

import com.bolo422.geolocation.config.UrlProperties;
import com.bolo422.geolocation.geolocation.model.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableConfigurationProperties(UrlProperties.class)
@RequiredArgsConstructor
public class LocalizationServiceIntegration {
    @Autowired
    private RestTemplate restTemplate;

    private final UrlProperties urlProperties;

    public Coordinate findCoordinateByZipCode(String zipCode) {
        return restTemplate.getForObject(urlProperties.localization() + zipCode, Coordinate.class);
    }
}
