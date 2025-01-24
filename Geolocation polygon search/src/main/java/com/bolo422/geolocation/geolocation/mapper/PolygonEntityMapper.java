package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.PolygonEntity;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public interface PolygonEntityMapper {

    static PolygonEntity mapPolygon(List<Point>coordinates, Coordinate storeCoordinates, String name) {
        return PolygonEntity.builder()
                .name(buildName(name))
                .geometry(new GeoJsonPolygon(coordinates))
                .storeCoordinates(new GeoJsonPoint(storeCoordinates.lng(), storeCoordinates.lat()))
                .build();
    }

    private static String buildName(String name) {
        final var now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).toString();
        return (StringUtils.hasText(name) ? name : "Polygon") + " - " + now;
    }
}
