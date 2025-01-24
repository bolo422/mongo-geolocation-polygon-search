package com.bolo422.geolocation.geolocation;

import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.PolygonResponseWrapper;
import com.bolo422.geolocation.geolocation.model.SavePolygonRequest;
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
}
