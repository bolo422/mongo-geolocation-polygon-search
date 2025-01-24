package com.bolo422.geolocation.geolocation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Document(collection = "polygons")
public record PolygonEntity(
        @Id
        String id,
        String name,
        GeoJsonPolygon geometry
) {
    public PolygonEntity(List<Point> coordinates) {
        this(
                null,
                "Polygon | " + LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
                , new GeoJsonPolygon(coordinates)
        );
    }
}
