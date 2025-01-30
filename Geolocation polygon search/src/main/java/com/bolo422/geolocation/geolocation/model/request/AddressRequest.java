package com.bolo422.geolocation.geolocation.model.request;

@Deprecated
public record AddressRequest(
        String name,
        String city,
        String neighborhood
) {
}
