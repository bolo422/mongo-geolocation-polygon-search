package com.bolo422.geolocation.geolocation.mapper;

import com.bolo422.geolocation.geolocation.model.entity.StoreEntity;
import com.bolo422.geolocation.geolocation.model.Subsidiary;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Collection;

public interface StoreEntityMapper {
    static Collection<StoreEntity> mapFrom(Collection<Subsidiary> subsidiaries) {
        return subsidiaries.stream()
                .map(StoreEntityMapper::mapFrom)
                .toList();
    }

    static StoreEntity mapFrom(Subsidiary subsidiary) {
        return StoreEntity.builder()
                .code(subsidiary.code())
                .friendlyName(subsidiary.friendlyName())
                .geometry(mapCoordinate(subsidiary.address()))
                .build();
    }

    static GeoJsonPoint mapCoordinate(Subsidiary.Address address) {
        return new GeoJsonPoint(address.longitude(), address.latitude());
    }
}