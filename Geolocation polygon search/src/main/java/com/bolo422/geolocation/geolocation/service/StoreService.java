package com.bolo422.geolocation.geolocation.service;

import com.bolo422.geolocation.geolocation.integration.SubsidiariesServiceIntegration;
import com.bolo422.geolocation.geolocation.mapper.StoreEntityMapper;
import com.bolo422.geolocation.geolocation.model.entity.StoreEntity;
import com.bolo422.geolocation.geolocation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final SubsidiariesServiceIntegration subsidiariesServiceIntegration;
    private final StoreRepository storeRepository;
    private static final List<String> STATES = List.of("RS", "SC", "PR", "SP");

    public void updateStores() {
        final var subsidiaries = STATES.stream()
                .map(subsidiariesServiceIntegration::findSubsidiaries)
                .flatMap(List::stream)
                .toList();

        final var stores = StoreEntityMapper.mapFrom(subsidiaries);
        final var existingStoreCodes = storeRepository.findAll()
                .stream()
                .map(StoreEntity::code)
                .collect(Collectors.toSet());

        final var newStores = stores.stream()
                .filter(store -> !existingStoreCodes.contains(store.code()))
                .toList();

        final var result = storeRepository.saveAll(newStores);

        log.info("Saved {} new stores", result.size());
        log.info("New stores: {}", result);
    }

    public List<StoreEntity> getAllStores() {
        return storeRepository.findAll()
                // sort by deliveryRadius. Nulls last
                .stream()
                .sorted((s1, s2) -> {
                    if (s1.deliveryRadius() == null && s2.deliveryRadius() == null) {
                        return 0;
                    }
                    if (s1.deliveryRadius() == null) {
                        return 1;
                    }
                    if (s2.deliveryRadius() == null) {
                        return -1;
                    }
                    return s2.deliveryRadius().compareTo(s1.deliveryRadius());
                })
                .toList();
    }

    public StoreEntity findStoreByCodeOrDefault(Long code) {
        final var store = findStoreByCode(code);
        if (store == null) {
            log.warn("Store not found for code: {}. Changing to default store 731", code);
            return StoreEntity.builder()
                    .code(731L)
                    .friendlyName("PANVEL HOSPITAL SANTA CLARA")
                    .geometry(new GeoJsonPoint(-51.2232568, -30.0306138))
                    .build();
        }
        return store;
    }

    public StoreEntity findStoreByCode(Long code) {
        log.info("Finding store by code: {}", code);
        final var store = storeRepository.findByCode(code);
        log.info("Found store: {}", store);
        return store;
    }

    public void updateStore(StoreEntity updatedStore) {
        log.info("Updating store: {}", updatedStore);
        storeRepository.save(updatedStore);
    }

    public void removeStoresDuplicates() {
        // get all stores
        // group by code
        // remove one from all groups
        // take the remaining results
        // delete them
        final var stores = storeRepository.findAll();
        final var storeGroups = stores.stream()
                .collect(Collectors.groupingBy(StoreEntity::code));
        storeGroups.values().stream()
                .filter(storeEntities -> storeEntities.size() > 1)
                .map(storeEntities -> storeEntities.subList(1, storeEntities.size()))
                .flatMap(List::stream)
                .forEach(this::deleteStore);
    }

    public void deleteStore(StoreEntity storeEntity) {
        log.info("Deleting store: {}", storeEntity);
        storeRepository.delete(storeEntity);
    }
}