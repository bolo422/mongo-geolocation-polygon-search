package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.entity.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.entity.StoreEntity;
import com.bolo422.geolocation.geolocation.model.response.PolygonResponseWrapper;
import com.bolo422.geolocation.geolocation.utils.CoordinateUtils;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface PolygonResponseMapper {

    static PolygonResponseWrapper mapFrom(Map<PolygonEntity, StoreEntity> polygons) {
        return mapFrom(polygons, null);
    }

    static PolygonResponseWrapper mapFrom(Map<PolygonEntity, StoreEntity> polygons,
                                          Coordinate userCoordinate
    ) {
        if (CollectionUtils.isEmpty(polygons)) {
            return PolygonResponseWrapper.builder()
                    .polygons(Collections.emptyList())
                    .build();
        }

        return PolygonResponseWrapper.builder()
                .polygons(mapPolygons(polygons, userCoordinate))
                .build();
    }

    private static List<PolygonResponseWrapper.PolygonResponse> mapPolygons(Map<PolygonEntity, StoreEntity> polygons,
                                                                            Coordinate userCoordinate
    ) {
        return polygons.entrySet().stream()
                .map(entry -> mapPolygon(userCoordinate, entry))
                .sorted((p1, p2) -> Double.compare(p2.areaSize(), p1.areaSize()))
                .toList();
    }

    private static PolygonResponseWrapper.PolygonResponse mapPolygon(Coordinate userCoordinate,
                                                                     Map.Entry<PolygonEntity, StoreEntity> polygonEntry
    ) {
        final var polygon = polygonEntry.getKey();
        final var store = polygonEntry.getValue();

        return PolygonResponseWrapper.PolygonResponse.builder()
                .name(polygon.name())
                .coordinates(mapCoordinates(polygon))
                .storeCoordinates(mapCoordinate(store.geometry()))
                .storeName(store.code() + " - " + store.friendlyName())
                .distance(calculateDistance(store.geometry(), userCoordinate))
                .areaSize(CoordinateUtils.calculatePolygonArea(polygon.geometry()))
                .deliveryType(polygon.deliveryType())
                .build();
    }

    static double calculateDistance(GeoJsonPoint point, Coordinate coordinate) {
        if(coordinate == null) return 0;

        final var lat = coordinate.lat();
        final var lng = coordinate.lng();
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
