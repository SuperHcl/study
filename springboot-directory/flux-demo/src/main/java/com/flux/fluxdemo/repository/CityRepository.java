package com.flux.fluxdemo.repository;

import com.flux.fluxdemo.domain.City;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Hu.ChangLiang
 * @date 2022/9/19 09:28
 */
@Repository
public class CityRepository {
    private final Map<Long, City> repository = new ConcurrentHashMap<>(8);
    private static final AtomicLong idGenerator = new AtomicLong(0);


    public long save(City city) {
        long id = idGenerator.incrementAndGet();
        city.setId(id);
        repository.put(id, city);
        return id;
    }

    public Collection<City> findAll() {
        return repository.values();
    }

    public City findCityById(long id) {
        return repository.get(id);
    }

    public long updateCity(City city) {
        Long id = city.getId();
        repository.put(id, city);
        return id;
    }

    public City deleteById(long id) {
        return repository.remove(id);
    }

}
