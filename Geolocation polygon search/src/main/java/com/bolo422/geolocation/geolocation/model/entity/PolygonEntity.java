package com.bolo422.geolocation.geolocation.model.entity;

import com.bolo422.geolocation.geolocation.enus.DeliveryTypeEnum;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "polygons")
@Builder(toBuilder = true)
public record PolygonEntity(
        @Id
        String id,
        String name,
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        GeoJsonPolygon geometry,
        Long storeCode,
        DeliveryTypeEnum deliveryType
) {
}
