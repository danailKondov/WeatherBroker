package com.bellintegrator.weatherbroker.jms;

import com.bellintegrator.weatherbroker.model.WeatherCondition;
import com.bellintegrator.weatherbroker.model.WeatherForecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class WeatherJmsProducer {

    private final Logger log = LoggerFactory.getLogger(WeatherJmsProducer.class);

    private JmsTemplate jmsTemplate;

    @Autowired
    public WeatherJmsProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendActualWeather(final String topicName, final WeatherCondition condition) {
        log.info("sending actual weather in JMS...");
        jmsTemplate.convertAndSend(topicName, condition);
        log.info("actual weather was send");
    }

    public void sendWeatherForecast(final String topicName, final WeatherForecast forecast) {
        log.info("sending forecast weather in JMS...");
        jmsTemplate.convertAndSend(topicName, forecast);
        log.info("forecast was send");
    }
}
