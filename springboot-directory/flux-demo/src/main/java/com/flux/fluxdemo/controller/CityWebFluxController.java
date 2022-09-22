package com.flux.fluxdemo.controller;

import com.flux.fluxdemo.domain.City;
import com.flux.fluxdemo.handler.CityHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * flux city controller
 * @author Hu.ChangLiang
 * @date 2022/9/22 17:04
 */
@RestController
@RequestMapping("/city")
public class CityWebFluxController {
    @Resource
    private CityHandler cityHandler;

    @GetMapping("/{id}")
    public Mono<City> findById(@PathVariable("id") Long id) {
        return cityHandler.findCityById(id);
    }

    @GetMapping
    public Flux<City> findAll() {
        return cityHandler.findAllCities();
    }

    @PostMapping("/create")
    public Mono<Long> create(@RequestBody City city) {
        return cityHandler.save(city);
    }

    @PostMapping("/create111")
    public City createa(@RequestBody City city) {
        return city;
    }

    @PutMapping
    public Mono<Long> update(@RequestBody City city) {
        return cityHandler.modify(city);
    }

    @DeleteMapping("/{id}")
    public Mono<City> deleteById(@PathVariable("id") Long id) {
        return cityHandler.deleteCity(id);
    }
}
