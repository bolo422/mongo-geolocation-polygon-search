package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.enus.DeliveryTypeEnum;
import com.bolo422.geolocation.geolocation.model.entity.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.request.SavePolygonRequest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public interface PolygonEntityMapper {

    static PolygonEntity mapPolygon(List<Point> polygonCoordinates,
                                    SavePolygonRequest savePolygonRequest
    ) {
        return mapPolygon(
                polygonCoordinates,
                savePolygonRequest.storeCode(),
                savePolygonRequest.name(),
                savePolygonRequest.deliveryType()
        );
    }

    static PolygonEntity mapPolygon(List<Point> coordinates,
                                    Long storeCode, String name,
                                    DeliveryTypeEnum deliveryType
    ) {
        return PolygonEntity.builder()
                .name(buildName(name))
                .geometry(new GeoJsonPolygon(coordinates))
//                .storeCoordinates(new GeoJsonPoint(storeCoordinates.lng(), storeCoordinates.lat()))
                .storeCode(storeCode)
                .deliveryType(deliveryType)
                .build();
    }

    private static String buildName(String name) {
        final var now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).toString();
        return (StringUtils.hasText(name) ? name : "Polygon") + " - " + now;
    }
}
