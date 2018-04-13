package com.bellintegrator.weatherbroker.service;

public interface CityWeatherService {

    void getWeatherForCity(String cityName, String degreeParam, String typeInfo);
}
