package com.bolo422.geolocation.geolocation.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Coordinate(
        double lat,
        @JsonAlias("lon")
        double lng
) {
}
