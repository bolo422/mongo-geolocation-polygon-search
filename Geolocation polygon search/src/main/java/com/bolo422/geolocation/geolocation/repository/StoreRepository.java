package com.bolo422.geolocation.geolocation.repository;

import com.bolo422.geolocation.geolocation.model.entity.StoreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StoreRepository extends MongoRepository<StoreEntity, String> {
    // each store has an attribute called "code" wich is a long. Make a find by code method
    @Query("{ 'code' : ?0 }")
    StoreEntity findByCode(Long code);
}
