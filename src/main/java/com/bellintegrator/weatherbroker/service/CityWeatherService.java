package com.bellintegrator.weatherbroker.service;

import com.bellintegrator.weatherbroker.dao.ExampleRepository;
import com.bellintegrator.weatherbroker.model.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityWeatherService {

    private ExampleRepository repository;

    @Autowired
    public CityWeatherService(ExampleRepository repository) {
        this.repository = repository;
    }

    public void addCity(String cityName) {
        repository.save(new Example(100, cityName));
    }
}
