package com.bolo422.geolocation.geolocation;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolygonsRepository extends MongoRepository<Coordinate, String> {
}
