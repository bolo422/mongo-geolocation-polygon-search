package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.PolygonResponseWrapper;
import com.bolo422.geolocation.geolocation.utils.CoordinateUtils;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public interface PolygonResponseMapper {

    static PolygonResponseWrapper mapFrom(List<PolygonEntity> polygonEntities) {
        return mapFrom(polygonEntities, 0, 0);
    }

    static PolygonResponseWrapper mapFrom(List<PolygonEntity> polygonEntities, double lat, double lng) {
        if (CollectionUtils.isEmpty(polygonEntities)) {
            return PolygonResponseWrapper.builder()
                    .polygons(Collections.emptyList())
                    .build();
        }

        return PolygonResponseWrapper.builder()
                .polygons(mapPolygons(polygonEntities, lat, lng))
                .build();
    }

    private static List<PolygonResponseWrapper.PolygonResponse> mapPolygons(
            List<PolygonEntity> polygonEntities, double lat, double lng
    ) {
        return polygonEntities.stream()
                .map(polygonEntity -> PolygonResponseWrapper.PolygonResponse.builder()
                        .name(polygonEntity.name())
                        .coordinates(mapCoordinates(polygonEntity))
                        .storeCoordinates(mapCoordinate(polygonEntity.storeCoordinates()))
                        .distance(calculateDistance(polygonEntity.storeCoordinates(), lat, lng))
                        .build())
                .toList();
    }

    static double calculateDistance(GeoJsonPoint point, double lat, double lng) {
        if (lat == 0 || lng == 0) return 0;

        return CoordinateUtils.calculateDistance(lat, lng, point.getY(), point.getX());
    }

    static Coordinate mapCoordinate(GeoJsonPoint point) {
        return new Coordinate(point.getY(), point.getX());
    }

    private static List<Coordinate> mapCoordinates(PolygonEntity polygonEntity) {
        return polygonEntity.geometry().getCoordinates().getFirst().getCoordinates().stream()
                .map(point -> new Coordinate(point.getY(), point.getX()))
                .toList();
    }
}
