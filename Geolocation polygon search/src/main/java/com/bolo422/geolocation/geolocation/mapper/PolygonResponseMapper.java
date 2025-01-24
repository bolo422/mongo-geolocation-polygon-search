package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.PolygonResponseWrapper;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public interface PolygonResponseMapper {

    static PolygonResponseWrapper mapFrom(List<PolygonEntity> polygonEntities) {
        if (CollectionUtils.isEmpty(polygonEntities)) {
            return PolygonResponseWrapper.builder()
                    .polygons(Collections.emptyList())
                    .build();
        }

        return PolygonResponseWrapper.builder()
                .polygons(mapPolygons(polygonEntities))
                .build();
    }

    private static List<PolygonResponseWrapper.PolygonResponse> mapPolygons(List<PolygonEntity> polygonEntities) {
        return polygonEntities.stream()
                .map(polygonEntity -> PolygonResponseWrapper.PolygonResponse.builder()
                        .name(polygonEntity.name())
                        .coordinates(mapCoordinates(polygonEntity))
                        .build())
                .toList();
    }

    private static List<Coordinate> mapCoordinates(PolygonEntity polygonEntity) {
        return polygonEntity.geometry().getCoordinates().getFirst().getCoordinates().stream()
                .map(point -> new Coordinate(point.getY(), point.getX()))
                .toList();
    }
}
