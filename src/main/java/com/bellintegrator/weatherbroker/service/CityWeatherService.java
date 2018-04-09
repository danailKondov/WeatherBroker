package com.bellintegrator.weatherbroker.service;

import com.bellintegrator.weatherbroker.dao.ExampleRepository;
import com.bellintegrator.weatherbroker.model.Example;
import com.bellintegrator.weatherbroker.views.WeatherActualView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CityWeatherService {

    private ExampleRepository repository;

    private RestTemplate restTemplate;

    @Autowired
    public CityWeatherService(ExampleRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public void getWeatherForCity(String cityName, String degreeParam, String typeInfo) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("cityName", cityName);
        if ("celsius".equals(degreeParam)) {
            vars.put("degreeParam", "c");
        } else {
            vars.put("degreeParam", "f");
        }
        if ("current".equals(typeInfo)) {
            vars.put("typeInfo", "item.condition");
        } else {
            vars.put("typeInfo", "item.forecast");
        }

        ResponseEntity<WeatherActualView> result = restTemplate.getForEntity("https://query.yahooapis.com/v1/public/yql?" +
                "q=select {typeInfo} from weather.forecast where woeid in (select woeid from geo.places(1) " +
                "where text=\"{cityName}\") and u=\"{degreeParam}\"&" +
                "format=json&env= store://datatables.org/alltableswithkeys", WeatherActualView.class, vars);

        WeatherActualView view = result.getBody();
    }
}
