package com.bolo422.geolocation.store;

import com.bolo422.geolocation.config.UrlProperties;
import com.bolo422.geolocation.store.model.SubsidiariesWrapper;
import com.bolo422.geolocation.store.model.Subsidiary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@EnableConfigurationProperties(UrlProperties.class)
@RequiredArgsConstructor
public class SubsidiariesServiceIntegration {
    @Autowired
    private RestTemplate restTemplate;

    private final UrlProperties urlProperties;

    public List<Subsidiary> findSubsidiaries() {
        final var url = urlProperties.subsidiaries() + "?pageNumber=0&pageSize=1000&sort=ASC&uf=RS";
        return Optional.ofNullable(restTemplate.getForObject(url, SubsidiariesWrapper.class))
                .map(SubsidiariesWrapper::content)
                .orElse(Collections.emptyList());
    }
}
