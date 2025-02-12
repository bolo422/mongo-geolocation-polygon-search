package com.bolo422.geolocation.geolocation.model;

public record Subsidiary(
        Long code,
        String friendlyName,
        Address address,
        Double deliveryRadius
) {
    public record Address(
            Double latitude,
            Double longitude
    ){}
}
