package com.bolo422.geolocation.geolocation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Document(collection = "polygons")
public record PolygonEntity(
        @Id
        String id,
        String name,
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        GeoJsonPolygon geometry,
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        GeoJsonPoint storeCoordinates
) {
    public PolygonEntity(List<Point> coordinates, Coordinate storeCoordinates) {
        this(
                null,
                "Polygon | " + LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                new GeoJsonPolygon(coordinates),
                new GeoJsonPoint(storeCoordinates.lng(), storeCoordinates.lat())
        );
    }
}
