package com.bellintegrator.weatherbroker.dao;

import com.bellintegrator.weatherbroker.model.Example;
import org.springframework.data.repository.CrudRepository;

public interface ExampleRepository extends CrudRepository<Example, Long> {
}
