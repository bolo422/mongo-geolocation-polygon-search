package com.bolo422.geolocation.store;

import com.bolo422.geolocation.store.model.StoreEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
