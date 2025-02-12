package com.bolo422.geolocation.geolocation.model.request;

public record SaveDeliveryRadiusRequest(
        Long storeCode,
        Double deliveryRadius
) {
}
