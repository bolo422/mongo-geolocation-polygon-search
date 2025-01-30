package com.bolo422.geolocation.geolocation.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverpassIntegration {
//    private static final String OVERPASS_URL = "https://overpass-api.de/api/interpreter";
    private static final String OVERPASS_URL = "http://localhost:3002/api/interpreter";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<List<double[]>> getNeighborhoodCoordinates(String city, String neighborhood) {
        final var query = buildOverpassQuery(city, neighborhood);

        try {
            final var response = restTemplate.postForObject(OVERPASS_URL, query, String.class);
            return extractCoordinates(response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar Overpass API", e);
        }
    }

    private String buildOverpassQuery(String city, String neighborhood) {
        return """
                [out:json];
                area[name="%s"]->.city;
                relation["name"="%s"]["boundary"="administrative"](area.city);
                out geom;
                """.formatted(city, neighborhood);
    }

    private List<List<double[]>> extractCoordinates(String jsonResponse) throws Exception {
        List<List<double[]>> polygons = new ArrayList<>();
        final var root = objectMapper.readTree(jsonResponse);

        for (final var element : root.path("elements")) {
            if (element.has("geometry")) {
                List<double[]> polygon = new ArrayList<>();
                for (JsonNode node : element.path("geometry")) {
                    final var lat = node.get("lat").asDouble();
                    final var lon = node.get("lon").asDouble();
                    polygon.add(new double[]{lat, lon});
                }
                polygons.add(polygon);
            }
        }
        return polygons;
    }
}