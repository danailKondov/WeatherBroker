package com.bellintegrator.weatherbroker.service.impl;

import com.bellintegrator.weatherbroker.dao.WeatherConditionRepository;
import com.bellintegrator.weatherbroker.dao.WeatherForecastRepository;
import com.bellintegrator.weatherbroker.exceptionhandler.exceptions.WeatherException;
import com.bellintegrator.weatherbroker.jms.WeatherJmsProducer;
import com.bellintegrator.weatherbroker.model.ForecastForDay;
import com.bellintegrator.weatherbroker.model.WeatherCondition;
import com.bellintegrator.weatherbroker.model.WeatherForecast;
import com.bellintegrator.weatherbroker.service.CityWeatherService;
import com.bellintegrator.weatherbroker.views.actual.WeatherActualView;
import com.bellintegrator.weatherbroker.views.forecast.Channel;
import com.bellintegrator.weatherbroker.views.forecast.WeatherForecastView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CityWeatherServiceImpl implements CityWeatherService {

    private final Logger log = LoggerFactory.getLogger(CityWeatherServiceImpl.class);
    private static final String TOPIC_NAME_ACTUAL_WEATHER = "weatherCondition.topic";
    private static final String TOPIC_NAME_FORECAST_WEATHER = "weatherForecast.topic";

    private WeatherJmsProducer jmsProducer;
    private RestTemplate restTemplate;

    @Autowired
    public CityWeatherServiceImpl(WeatherJmsProducer jmsProducer, RestTemplate restTemplate) {
        this.jmsProducer = jmsProducer;
        this.restTemplate = restTemplate;
    }

    /**
     * Метод получает параметры для запроса прогноза погоды или актуального состояния и
     * помещает результат в JMS очередь.
     *
     * @param cityName имя города
     * @param degreeParam единицы измерения температуры
     * @param typeInfo прогноз погоды или актуальное состояние
     */
    @Override
    public void getWeatherForCity(String cityName, String degreeParam, String typeInfo) {
        Map<String, String> vars = setParamsForWeatherRequest(cityName, degreeParam, typeInfo);
        if ("current".equals(typeInfo)) {
            requestForActualWeather(cityName, degreeParam, vars);
        } else if ("forecast".equals(typeInfo)){
            requestForWeatherForecast(cityName, degreeParam, vars);
        } else {
            throw new WeatherException("Wrong type of info request parameter");
        }
    }

    /**
     * Получаем прогноз погоды.
     *
     * @param cityName имя города
     * @param degreeParam единицы измерения температуры
     * @param vars блок переменных для запроса
     */
    private void requestForWeatherForecast(String cityName, String degreeParam, Map<String, String> vars) {
        log.info("Requesting for forecast...");
        ResponseEntity<WeatherForecastView> result = restTemplate.getForEntity("https://query.yahooapis.com/v1/public/yql?" +
                "q=select {typeInfo} from weather.forecast where woeid in (select woeid from geo.places(1) " +
                "where text=\"{cityName}\") and u=\"{degreeParam}\"&" +
                "format=json&env= store://datatables.org/alltableswithkeys", WeatherForecastView.class, vars);
        WeatherForecastView view = result.getBody();
        log.info("Request result is ready");
        WeatherForecast forecast = null;
        if (view.getQuery().getResults() != null) {
            forecast = transformDtoToEntity(cityName, degreeParam, view);
            log.info("Forecast view transformed to DTO");
        } else {
            throw new WeatherException("Wrong city name!");
        }
        jmsProducer.sendWeatherForecast(TOPIC_NAME_FORECAST_WEATHER, forecast);
    }

    /**
     * Получаем описание текущей погоды.
     *
     * @param cityName имя города
     * @param degreeParam единицы измерения температуры
     * @param vars блок переменных для запроса
     */
    private void requestForActualWeather(String cityName, String degreeParam, Map<String, String> vars) {
        log.info("Requesting for actual weather condition...");
        ResponseEntity<WeatherActualView> result = restTemplate.getForEntity("https://query.yahooapis.com/v1/public/yql?" +
                "q=select {typeInfo} from weather.forecast where woeid in (select woeid from geo.places(1) " +
                "where text=\"{cityName}\") and u=\"{degreeParam}\"&" +
                "format=json&env= store://datatables.org/alltableswithkeys", WeatherActualView.class, vars);
        WeatherActualView view = result.getBody();
        log.info("Request result is ready");
        WeatherCondition weather = null;
        if (view.getQuery().getResults() != null) {
            weather = new WeatherCondition(view, cityName, degreeParam);
            log.info("Actual weather condition view transformed to DTO");
        } else {
            throw new WeatherException("Wrong city name!");
        }
        jmsProducer.sendActualWeather(TOPIC_NAME_ACTUAL_WEATHER, weather);
    }

    private Map<String, String> setParamsForWeatherRequest(String cityName, String degreeParam, String typeInfo) {
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
        return vars;
    }

    private WeatherForecast transformDtoToEntity(String cityName, String degreeParam, WeatherForecastView view) {
        log.info("Beginning transformation from WeatherForecastView to WeatherForecast...");
        WeatherForecast forecast = new WeatherForecast();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String created = view.getQuery().getCreated();
        if (created != null) {
            try {
                forecast.setDate(format.parse(view.getQuery().getCreated()));
            } catch (ParseException e) {
                throw new WeatherException("Can't parse the date of the weather forecast", e);
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
        log.info("End of transformation from WeatherForecastView to WeatherForecast");
        return forecast;
    }
}
