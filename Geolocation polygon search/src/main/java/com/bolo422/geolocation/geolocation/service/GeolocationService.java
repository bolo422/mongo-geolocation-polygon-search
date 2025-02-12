package com.bolo422.geolocation.geolocation.service;

import com.bolo422.geolocation.geolocation.enus.DeliveryTypeEnum;
import com.bolo422.geolocation.geolocation.mapper.PolygonEntityMapper;
import com.bolo422.geolocation.geolocation.mapper.PolygonResponseMapper;
import com.bolo422.geolocation.geolocation.model.Coordinate;
import com.bolo422.geolocation.geolocation.model.entity.PolygonEntity;
import com.bolo422.geolocation.geolocation.model.entity.StoreEntity;
import com.bolo422.geolocation.geolocation.model.request.EditPolygonRequest;
import com.bolo422.geolocation.geolocation.model.request.SaveDeliveryRadiusRequest;
import com.bolo422.geolocation.geolocation.model.request.SavePolygonRequest;
import com.bolo422.geolocation.geolocation.model.response.PolygonResponseWrapper;
import com.bolo422.geolocation.geolocation.repository.PolygonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeolocationService {
    private final PolygonRepository polygonRepository;
    private final StoreService storeService;

    /**
     * Salva um polígono no banco de dados
     *
     * @param savePolygonRequest request com o nome e as coordenadas do polígono
     * @return mensagem de sucesso
     */
    public String savePolygon(SavePolygonRequest savePolygonRequest) {
        // as docs do Mongo pedem que a último coordenada seja igual a primeira para fechar o polígono
        final var coordinates = savePolygonRequest.coordinates().getFirst();
        if (!coordinates.getFirst().equals(coordinates.getLast())) {
            coordinates.add(coordinates.getFirst());
        }

        final var polygonCoordinates = coordinatesToPoints(coordinates);

        final var polygonEntity = PolygonEntityMapper.mapPolygon(polygonCoordinates, savePolygonRequest);

        polygonRepository.save(polygonEntity);

        return "Polígono salvo com sucesso!";
    }

    private static List<Point> coordinatesToPoints(List<Coordinate> coordinates) {
        return coordinates.stream()
                .map(coordinate -> new Point(coordinate.lng(), coordinate.lat()))
                .toList();
    }

    /**
     * Encontra um polígono que intersecta com o ponto informado
     *
     * @param lat latitude
     * @param lng longitude
     * @return PolygonResponseWrapper
     * @see PolygonResponseWrapper
     */
    public PolygonResponseWrapper findPolygonByPoint(double lat, double lng) {
        final var polygons = polygonRepository.findPolygonsByPoint(lng, lat);
        final var polygonsWithStores = getPolygonsWithStores(polygons);
        final var userCoordinates = new Coordinate(lat, lng);

        return PolygonResponseMapper.mapFrom(polygonsWithStores, userCoordinates);
    }

    public PolygonResponseWrapper findPolygonByPointAndDeliveryType(double lat, double lng, DeliveryTypeEnum deliveryType) {
        final var polygons = polygonRepository.findPolygonsByPointAndDeliveryType(lng, lat, deliveryType.name());
        final var polygonsWithStores = getPolygonsWithStores(polygons);

        return PolygonResponseMapper.mapFrom(polygonsWithStores);
    }

    /**
     * Encontra todos os polígonos salvos no banco de dados
     *
     * @return PolygonResponseWrapper
     * @see PolygonResponseWrapper
     */
    public PolygonResponseWrapper findAll() {
        final var polygons = polygonRepository.findAll();
        final var polygonsWithStores = getPolygonsWithStores(polygons);
        return PolygonResponseMapper.mapFrom(polygonsWithStores);
    }

    private Map<PolygonEntity, StoreEntity> getPolygonsWithStores(List<PolygonEntity> polygons) {
        return polygons.stream()
                .collect(Collectors.toMap(
                        polygon -> polygon,
                        polygon -> storeService.findStoreByCodeOrDefault(polygon.storeCode()))
                );
    }

    public List<String> getDeliveryTypes() {
        return  Stream.of(DeliveryTypeEnum.values())
                .map(Enum::name)
                .toList();
    }

    public String editPolygon(EditPolygonRequest request) {
        // find polygon by name and update it
        final var polygon = polygonRepository.findPolygonByName(request.name());
        if (polygon == null) {
            log.error("Polígono não encontrado");
            throw new IllegalArgumentException("Polígono não encontrado");
        }

        final var coordinates = request.coordinates();

        if (CollectionUtils.isEmpty(coordinates)) {
            log.warn("Deletando polígono: ' {}'", polygon.name());
            polygonRepository.delete(polygon);
            return "Polígono deletado com sucesso!";
        }

        if (!coordinates.getFirst().equals(coordinates.getLast())) {
            coordinates.add(coordinates.getFirst());
        }
        final var points = coordinatesToPoints(coordinates);
        final var editedPolygon = polygon.toBuilder()
                .geometry(new GeoJsonPolygon(points))
                .build();


        polygonRepository.save(editedPolygon);

        return "Polígono editado com sucesso!";
    }

    public String saveDeliveryRadius(SaveDeliveryRadiusRequest saveDeliveryRadiusRequest) {
        final var store = storeService.findStoreByCode(saveDeliveryRadiusRequest.storeCode());

        if(store == null) {
            log.error("Loja não encontrada");
            throw new IllegalArgumentException("Loja não encontrada");
        }

        final var updatedStore = store.toBuilder()
                .deliveryRadius(saveDeliveryRadiusRequest.deliveryRadius())
                .build();

        storeService.updateStore(updatedStore);

        return "Raio de entrega salvo com sucesso!";
    }

    // DISABLED
//    @Deprecated
//    public String saveAddress(AddressRequest addressRequest) {
//        final var polygon = overpassService.addressToPolygon(addressRequest.city(), addressRequest.neighborhood());
//        final var polygonPoints = polygon.getCoordinates().stream()
//                .flatMap(lineString -> lineString.getCoordinates().stream())
//                .toList();
//
//        final var polygonCoordiantes = polygonPoints.stream()
//                .map(point -> new Coordinate(point.getY(), point.getX()))
//                .toList();
//
//        final var storeCoordinates = CoordinateUtils.calculateCenter(polygonCoordiantes);
//
//        final var polygonEntity = PolygonEntityMapper.mapPolygon(polygonPoints, storeCoordinates, addressRequest.name());
//
//        polygonRepository.save(polygonEntity);
//
//        return "Endereço salvo com sucesso!";
//    }
}
