package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.model.entity.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.request.SavePolygonRequest;

import java.util.List;

import static com.bolo422.geolocation.geolocation.mapper.PolygonResponseMapper.mapCoordinates;

public interface SavePolygonRequestMapper {

    public static SavePolygonRequest fromEntity(PolygonEntity entity) {
        return new SavePolygonRequest(
                entity.name(),
                List.of(mapCoordinates(entity)),
                entity.deliveryType(),
                entity.storeCode()
        );
    }
}
