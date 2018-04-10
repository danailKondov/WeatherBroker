package com.bellintegrator.weatherbroker.dao;

import com.bellintegrator.weatherbroker.model.WeatherCondition;
import org.springframework.data.repository.CrudRepository;

public interface WeatherConditionRepository extends CrudRepository<WeatherCondition, Long> {
}
