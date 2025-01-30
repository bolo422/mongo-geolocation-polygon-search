package com.bolo422.geolocation.geolocation.controller;


import com.bolo422.geolocation.geolocation.model.entity.StoreEntity;
import com.bolo422.geolocation.geolocation.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/updateStores")
    public void updateStores() {
        storeService.updateStores();
    }

    @GetMapping("/allStores")
    public ResponseEntity<List<StoreEntity>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/{storeCode}")
    public ResponseEntity<StoreEntity> findStoreByCode(@PathVariable Long storeCode) {
        final var store = storeService.findStoreByCode(storeCode);
        if (store == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(store);
    }
}
