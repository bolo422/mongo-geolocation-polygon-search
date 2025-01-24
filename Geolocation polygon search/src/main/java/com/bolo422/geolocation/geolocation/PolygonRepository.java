package com.bolo422.geolocation.geolocation;

import com.bolo422.geolocation.geolocation.model.PolygonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PolygonRepository extends MongoRepository<PolygonEntity, String> {
    @Query("{ 'geometry' : { $geoIntersects: { $geometry: { type: 'Point', coordinates: [?0, ?1] } } } }")
    List<PolygonEntity> findPolygonsByPoint(double lng, double lat);
}
