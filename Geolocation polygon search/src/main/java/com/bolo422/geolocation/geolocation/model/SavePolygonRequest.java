package com.bolo422.geolocation.geolocation.model;

import java.util.List;

public record SavePolygonRequest(
        String name,
        List<Coordinate> coordinates
) {
}
