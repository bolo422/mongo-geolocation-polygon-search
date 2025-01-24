package com.bolo422.geolocation.geolocation.model;

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
            double distance
    ) {
    }
}
