package com.bolo422.geolocation.geolocation.model.request;

import com.bolo422.geolocation.geolocation.model.Coordinate;

import java.util.List;

public record EditPolygonRequest(
        String name,
        List<Coordinate> coordinates
) {
}
