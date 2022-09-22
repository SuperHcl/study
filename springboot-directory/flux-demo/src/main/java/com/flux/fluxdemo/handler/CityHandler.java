package com.flux.fluxdemo.handler;

import com.flux.fluxdemo.domain.City;
import com.flux.fluxdemo.repository.CityRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/13 17:11
 */
@Component
public class CityHandler {
    @Resource
    private CityRepository cityRepository;

    public Mono<ServerResponse> helloCity(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body("Hello city!", String.class);
    }

    public Mono<Long> save(City city) {
        long save = cityRepository.save(city);
        return Mono.create(longMonoSink -> longMonoSink.success(save));
    }

    public Mono<City> findCityById(Long id) {
        return Mono.justOrEmpty(cityRepository.findCityById(id));
    }

    public Flux<City> findAllCities() {
        return Flux.fromIterable(cityRepository.findAll());
    }

    public Mono<Long> modify(City city) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityRepository.updateCity(city)));
    }

    public Mono<City> deleteCity(Long id) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityRepository.deleteById(id)));
    }

}
