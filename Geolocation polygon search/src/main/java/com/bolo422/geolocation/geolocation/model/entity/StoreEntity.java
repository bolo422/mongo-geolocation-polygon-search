package com.bolo422.geolocation.geolocation.model.entity;

import lombok.Builder;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "stores")
public record StoreEntity(
        Long code,
        String friendlyName,
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        GeoJsonPoint geometry
) {
}
