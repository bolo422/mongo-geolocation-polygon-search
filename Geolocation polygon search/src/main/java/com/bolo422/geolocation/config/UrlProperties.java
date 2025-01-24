package com.bolo422.geolocation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
public record UrlProperties(
        String base,
        String subsidiaries,
        String localization
) {
}
