package com.bolo422.geolocation.geolocation.model.response;

import com.bolo422.geolocation.geolocation.model.Subsidiary;

import java.util.List;

public record SubsidiariesIntegrationResponseWrapper(
        List<Subsidiary> content
) {
}
