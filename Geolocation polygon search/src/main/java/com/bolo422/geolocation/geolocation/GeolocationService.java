package com.bolo422.geolocation.geolocation;

import com.bolo422.geolocation.geolocation.mapper.PolygonResponseMapper;
import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.PolygonResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    private final PolygonRepository polygonRepository;

    /**
     * Salva um polígono no banco de dados
     * @param coordinates
     * @return mensagem de sucesso
     */
    public String savePolygon(List<Coordinate> coordinates) {
        // as docs do Mongo pedem que a último coordenada seja igual a primeira para fechar o polígono
        if (!coordinates.getFirst().equals(coordinates.getLast())) {
            coordinates.add(coordinates.getFirst());
        }

        final var polygonCoordinates = coordinates.stream()
                .map(coordinate -> new Point(coordinate.lng(), coordinate.lat()))
                .toList();

        final var polygonEntity = new PolygonEntity(polygonCoordinates);

        polygonRepository.save(polygonEntity);

        return "Polígono salvo com sucesso!";
    }

    /**
     * Encontra um polígono que intersecta com o ponto informado
     * @param lat
     * @param lng
     * @return PolygonResponseWrapper
     * @see PolygonResponseWrapper
     */
    public PolygonResponseWrapper findPolygonByPoint(double lat, double lng) {
        final var polygons = polygonRepository.findPolygonsByPoint(lng, lat);
        return PolygonResponseMapper.mapFrom(polygons);
    }

    /**
     * Encontra todos os polígonos salvos no banco de dados
     * @return PolygonResponseWrapper
     * @see PolygonResponseWrapper
     */
    public PolygonResponseWrapper findAll() {
        final var polygons = polygonRepository.findAll();
        return PolygonResponseMapper.mapFrom(polygons);
    }
}
