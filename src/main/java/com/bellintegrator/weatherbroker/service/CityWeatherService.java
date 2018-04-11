package com.bellintegrator.weatherbroker.service;

import com.bellintegrator.weatherbroker.dao.WeatherConditionRepository;
import com.bellintegrator.weatherbroker.dao.WeatherForecastRepository;
import com.bellintegrator.weatherbroker.model.ForecastForDay;
import com.bellintegrator.weatherbroker.model.WeatherCondition;
import com.bellintegrator.weatherbroker.model.WeatherForecast;
import com.bellintegrator.weatherbroker.views.actual.WeatherActualView;
import com.bellintegrator.weatherbroker.views.forecast.Channel;
import com.bellintegrator.weatherbroker.views.forecast.Forecast;
import com.bellintegrator.weatherbroker.views.forecast.WeatherForecastView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Repository
@Transactional
public class CityWeatherService {

    private WeatherConditionRepository actualConditionRepository;
    private WeatherForecastRepository forecastRepository;

    private RestTemplate restTemplate;

    @Autowired
    public CityWeatherService(WeatherConditionRepository actualConditionRepository,
                              RestTemplate restTemplate,
                              WeatherForecastRepository forecastRepository) {
        this.actualConditionRepository = actualConditionRepository;
        this.restTemplate = restTemplate;
        this.forecastRepository = forecastRepository;
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

            // получаем данные по актуальному состоянию погоды
            ResponseEntity<WeatherActualView> result = restTemplate.getForEntity("https://query.yahooapis.com/v1/public/yql?" +
                    "q=select {typeInfo} from weather.forecast where woeid in (select woeid from geo.places(1) " +
                    "where text=\"{cityName}\") and u=\"{degreeParam}\"&" +
                    "format=json&env= store://datatables.org/alltableswithkeys", WeatherActualView.class, vars);

            WeatherActualView view = result.getBody();
            WeatherCondition weather = new WeatherCondition(view, cityName, degreeParam);
//            actualConditionRepository.save(weather); // здесь отправляем в JMS - а пока тестим БД
        } else {
            vars.put("typeInfo", "item.forecast");

            // получаем прогноз погоды
            ResponseEntity<WeatherForecastView> result = restTemplate.getForEntity("https://query.yahooapis.com/v1/public/yql?" +
                    "q=select {typeInfo} from weather.forecast where woeid in (select woeid from geo.places(1) " +
                    "where text=\"{cityName}\") and u=\"{degreeParam}\"&" +
                    "format=json&env= store://datatables.org/alltableswithkeys", WeatherForecastView.class, vars);
            WeatherForecastView view = result.getBody();
            WeatherForecast forecast = transformDtoToEntity(cityName, degreeParam, view);
            forecastRepository.save(forecast);
        }
    }

    private WeatherForecast transformDtoToEntity(String cityName, String degreeParam, WeatherForecastView view) {
        WeatherForecast forecast = new WeatherForecast();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String created = view.getQuery().getCreated();
        if (created != null) {
            try {
                forecast.setDate(format.parse(view.getQuery().getCreated()));
            } catch (ParseException e) {
                // TODO: выбросить кастомное исключение
            }
        }
        List<Channel> channels = view.getQuery().getResults().getChannel();
        Set<ForecastForDay> forecasts = new HashSet<>();
        if (channels != null) {
            for (Channel channel : channels) {
                forecasts.add(new ForecastForDay(channel.getItem().getForecast()));
            }
        }
        forecast.setForecasts(forecasts);
        forecast.setCity(cityName);
        forecast.setTempType(degreeParam);
        return forecast;
    }
}
