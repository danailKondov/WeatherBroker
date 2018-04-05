package com.bellintegrator.weatherbroker.service;

import com.bellintegrator.weatherbroker.dao.ExampleRepository;
import com.bellintegrator.weatherbroker.model.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private ExampleRepository repository;

    @Autowired
    public ExampleService(ExampleRepository repository) {
        this.repository = repository;
    }

    public Example getExampleById(Long id) {
        return repository.findById(id).get();
    }
}
