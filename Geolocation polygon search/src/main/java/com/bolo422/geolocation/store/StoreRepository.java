package com.bolo422.geolocation.store;

import com.bolo422.geolocation.store.model.StoreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<StoreEntity, String> {
}
