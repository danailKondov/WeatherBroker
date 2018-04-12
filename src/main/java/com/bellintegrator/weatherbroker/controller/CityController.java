package com.bellintegrator.weatherbroker.controller;

public interface CityController {
    void getWeatherForCity(String cityName, String degreeParam, String typeInfo);
}
