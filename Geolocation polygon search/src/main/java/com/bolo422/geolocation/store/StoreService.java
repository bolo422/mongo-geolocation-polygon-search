package com.bolo422.geolocation.store;

import com.bolo422.geolocation.store.mapper.StoreEntityMapper;
import com.bolo422.geolocation.store.model.StoreEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final SubsidiariesServiceIntegration subsidiariesServiceIntegration;
    private final StoreRepository storeRepository;

    public void updateStores() {
        final var subsidiaries = subsidiariesServiceIntegration.findSubsidiaries();
        final var stores = StoreEntityMapper.mapFrom(subsidiaries);
        final var existingStoreCodes = storeRepository.findAll()
                .stream()
                .map(StoreEntity::code)
                .collect(Collectors.toSet());

        final var newStores = stores.stream()
                .filter(store -> !existingStoreCodes.contains(store.code()))
                .collect(Collectors.toList());

        final var result = storeRepository.saveAll(newStores);
        log.info("Saved {} new stores", result.size());
        log.info("New stores: {}", result);
    }

    public List<StoreEntity> getAllStores() {
        return storeRepository.findAll();
    }
}
