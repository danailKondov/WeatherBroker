package com.bellintegrator.weatherbroker.model;

import com.bellintegrator.weatherbroker.views.WeatherActualView;

import java.util.Date;

public class WeatherCondition {

    private String city;
    private Date date;
    private Integer temp;
    private String description;

    public WeatherCondition() {
    }

    public WeatherCondition(WeatherActualView view, String cityName) {
        city = cityName;
        description = view.getQuery().getResults().getChannel().getItem().getCondition().getText();
        temp = view.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
        // еще парсим дату
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
