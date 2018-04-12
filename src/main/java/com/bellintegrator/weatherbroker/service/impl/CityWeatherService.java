package com.bellintegrator.weatherbroker.service.impl;

public interface CityWeatherService {

    void getWeatherForCity(String cityName, String degreeParam, String typeInfo);
}
