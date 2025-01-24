package com.bolo422.geolocation.store.model;

public record Subsidiary(
        Long code,
        String friendlyName,
        Address address
) {
    public record Address(
            Double latitude,
            Double longitude
    ){}
}
