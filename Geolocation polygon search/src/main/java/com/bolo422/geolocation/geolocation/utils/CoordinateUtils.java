package com.bolo422.geolocation.geolocation.utils;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.entity.PolygonEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinateUtils {

    private static final double EARTH_RADIUS = 6371;

    public static Coordinate calculateCenter(List<Coordinate> coordinates) {
        double sumLat = 0;
        double sumLng = 0;

        for (Coordinate coordinate : coordinates) {
            sumLat += coordinate.lat();
            sumLng += coordinate.lng();
        }

        final var centerLat = sumLat / coordinates.size();
        final var centerLng = sumLng / coordinates.size();

        return new Coordinate(centerLat, centerLng);
    }

    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static double calculatePolygonArea(GeoJsonPolygon polygon) {
        double area = 0.0;

        var geoJsonLineStrings = polygon.getCoordinates();
        if (geoJsonLineStrings.isEmpty()) {
            throw new IllegalArgumentException("Polygon does not contain valid coordinates.");
        }

        var coordinates = geoJsonLineStrings.get(0).getCoordinates();

        // Assuming the polygon is closed (first point == last point)
        for (int i = 0; i < coordinates.size() - 1; i++) {
            final var point1 = coordinates.get(i);
            final var point2 = coordinates.get(i + 1);

            // Shoelace formula
            area += point1.getX() * point2.getY();
            area -= point1.getY() * point2.getX();
        }

        area = Math.abs(area) / 2.0;
        return area;
    }
}
