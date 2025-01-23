package com.bolo422.geolocation.geolocation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Coordinate(
        @Id
        String id,
        double lat,
        double lng
) {
}
