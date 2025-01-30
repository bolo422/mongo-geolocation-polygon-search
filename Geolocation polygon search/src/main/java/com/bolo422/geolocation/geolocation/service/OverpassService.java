package com.bolo422.geolocation.geolocation.service;

import com.bolo422.geolocation.geolocation.integration.OverpassIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverpassService {

    private final OverpassIntegration overpassIntegration;

    public GeoJsonPolygon addressToPolygon(String city, String neighborhood) {
        final List<List<double[]>> coords = overpassIntegration.getNeighborhoodCoordinates(city, neighborhood);
        return mapGeoJsonPolygon(coords.getFirst());
    }

    private GeoJsonPolygon mapGeoJsonPolygon(List<double[]> coords) {
        final var points = coords.stream()
                .map(coord -> new Point(coord[0], coord[1]))
                .toList();

        return new GeoJsonPolygon(points);
    }
}
