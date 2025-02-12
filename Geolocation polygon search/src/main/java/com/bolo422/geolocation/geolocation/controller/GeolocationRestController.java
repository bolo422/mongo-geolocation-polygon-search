package com.bolo422.geolocation.geolocation.controller;

import com.bolo422.geolocation.geolocation.model.request.EditPolygonRequest;
import com.bolo422.geolocation.geolocation.model.request.SaveDeliveryRadiusRequest;
import com.bolo422.geolocation.geolocation.model.request.SavePolygonRequest;
import com.bolo422.geolocation.geolocation.model.response.PolygonResponseWrapper;
import com.bolo422.geolocation.geolocation.service.GeolocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GeolocationRestController {
    private final GeolocationService geolocationService;

    @PostMapping("/saveCoordinates")
    public ResponseEntity<String> saveCoordinates(@RequestBody SavePolygonRequest savePolygonRequest) {
        try {
            final var message = geolocationService.savePolygon(savePolygonRequest);
            log.info(message);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<PolygonResponseWrapper> findPolygonByPoint(@RequestParam double lat, @RequestParam double lng) {
        final var polygons = geolocationService.findPolygonByPoint(lat, lng);
        log.info("Polígonos encontrados: {}", polygons);
        return ResponseEntity.ok(polygons);
    }

    @GetMapping("/findAll")
    public ResponseEntity<PolygonResponseWrapper> findAll() {
        final var polygons = geolocationService.findAll();
        log.info("Polígonos encontrados: {}", polygons);
        return ResponseEntity.ok(polygons);
    }

    @GetMapping("/deliveryTypes")
    public ResponseEntity<List<String>> getDeliveryTypes(){
        log.info("Tipos de entrega encontrados: {}", geolocationService.getDeliveryTypes());
        return ResponseEntity.ok(geolocationService.getDeliveryTypes());
    }

    @PatchMapping("/editPolygon")
    public ResponseEntity<String> editPolygon(@RequestBody EditPolygonRequest request) {
        try {
            final var message = geolocationService.editPolygon(request);
            log.info(message);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saveDeliveryRadius")
    public ResponseEntity<String> saveDeliveryRadius(@RequestBody SaveDeliveryRadiusRequest saveDeliveryRadiusRequest) {
        try {
            final var message = geolocationService.saveDeliveryRadius(saveDeliveryRadiusRequest);
            log.info(message);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DISABLED
//    @PostMapping("/saveAddress")
//    @Deprecated
//    public ResponseEntity<String> saveAddress(@RequestBody AddressRequest addressRequest) {
//        try {
//            final var message = geolocationService.saveAddress(addressRequest);
//            log.info(message);
//            return ResponseEntity.ok(message);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
