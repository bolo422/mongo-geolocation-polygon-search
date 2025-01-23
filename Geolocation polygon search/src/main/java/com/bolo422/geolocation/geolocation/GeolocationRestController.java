package com.bolo422.geolocation.geolocation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeolocationRestController {

    @PostMapping("/saveCoordinates")
    public ResponseEntity<String> saveCoordinates(@RequestBody List<Coordinate> coordinates) {
        coordinateService.saveCoordinates(coordinates);
        return ResponseEntity.ok("Coordinates saved successfully");
    }
}
