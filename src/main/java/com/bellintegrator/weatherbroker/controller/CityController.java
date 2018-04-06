package com.bellintegrator.weatherbroker.controller;

import com.bellintegrator.weatherbroker.service.CityWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/weather")
public class CityController {

    private CityWeatherService service;

    @Autowired
    public CityController(CityWeatherService service) {
        this.service = service;
    }

    @GetMapping(value = "/cityname")
    public void getExample(@RequestParam("cityName") String cityName) {
        service.addCity(cityName);
    }
}
