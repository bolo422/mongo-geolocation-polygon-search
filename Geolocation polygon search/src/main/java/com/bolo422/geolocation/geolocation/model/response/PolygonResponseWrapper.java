package com.bolo422.geolocation.geolocation.model.response;

import com.bolo422.geolocation.geolocation.enus.DeliveryTypeEnum;
import com.bolo422.geolocation.geolocation.model.Coordinate;
import lombok.Builder;

import java.util.List;

@Builder
public record PolygonResponseWrapper(
        List<PolygonResponse> polygons
) {
    @Builder
    public record PolygonResponse(
            String name,
            List<Coordinate> coordinates,
            Coordinate storeCoordinates,
            String storeName,
            double distance,
            double areaSize,
            DeliveryTypeEnum deliveryType
    ) {
    }
}
