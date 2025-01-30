package com.bolo422.geolocation.geolocation.model.request;

import com.bolo422.geolocation.geolocation.enus.DeliveryTypeEnum;
import com.bolo422.geolocation.geolocation.model.Coordinate;

import java.util.List;

public record SavePolygonRequest(
        String name,
        List<List<Coordinate>> coordinates,
        DeliveryTypeEnum deliveryType,
        Long storeCode
) {
}
