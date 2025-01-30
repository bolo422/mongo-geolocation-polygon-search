package com.bolo422.geolocation.geolocation.repository;

import com.bolo422.geolocation.geolocation.model.entity.PolygonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PolygonRepository extends MongoRepository<PolygonEntity, String> {
    @Query("{ 'geometry' : { $geoIntersects: { $geometry: { type: 'Point', coordinates: [?0, ?1] } } } }")
    List<PolygonEntity> findPolygonsByPoint(double lng, double lat);

    @Query("{ 'geometry' : { $geoIntersects: { $geometry: { type: 'Point', coordinates: [?0, ?1] } } }, 'deliveryType': ?2 }")
    List<PolygonEntity> findPolygonsByPointAndDeliveryType(double lng, double lat, String deliveryType);

    @Query("{ 'name' : ?0 }")
    PolygonEntity findPolygonByName(String name);
}
