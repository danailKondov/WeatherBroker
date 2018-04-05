package com.bellintegrator.weatherbroker.controller;

import com.bellintegrator.weatherbroker.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/example")
public class ExampleController {

    private ExampleService service;

    @Autowired
    public ExampleController(ExampleService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getExample(@PathVariable("id") Long id) {
        return new ResponseEntity(service.getExampleById(id), HttpStatus.FOUND);
    }
}
