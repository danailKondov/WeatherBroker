package com.bellintegrator.weatherbroker.dao;

import com.bellintegrator.weatherbroker.model.WeatherForecast;
import org.springframework.data.repository.CrudRepository;

public interface WeatherForecastRepository extends CrudRepository<WeatherForecast, Long> {
}
